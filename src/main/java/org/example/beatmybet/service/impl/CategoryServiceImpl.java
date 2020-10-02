package org.example.beatmybet.service.impl;

import org.example.beatmybet.dto.CategoryDTO;
import org.example.beatmybet.entity.Category;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.CategoryRepository;
import org.example.beatmybet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public boolean create(String title) {

        return false; // TODO create category
    }

    public List<CategoryDTO> getMainCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(o -> Objects.isNull(o.getCategory()))
                .map(mapToCategoryDto)
                .collect(toList());
    }

    public List<CategoryDTO> getSubcategories(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category", id))
                .getSubCategories()
                .stream()
                .map(mapToCategoryDto)
                .collect(toList());
    }

    public CategoryDTO getById(Long id) {
        return categoryRepository.findById(id)
                .map(mapToCategoryDto)
                .orElseThrow(() -> new NotFoundException("category", id));
    }

    public CategoryDTO getDtoByTitle(String title) {
        return mapToCategoryDto.apply(categoryRepository.findByName(title));
    }

    public Category getEntityByTitle(String title) {
        Category category = categoryRepository.findByName(title);
        if(category != null) {
            return category;
        }
        throw new NotFoundException("There's no category with title " + title);
    }

    Function<Category, CategoryDTO> mapToCategoryDto = (category -> CategoryDTO.builder()
            .id(category.getId())
            .name(category.getName())
            .subCategories(category.getSubCategories().stream()
                    .map(subCat -> CategoryDTO.builder()
                            .id(subCat.getId())
                            .name(subCat.getName())
                            .build())
                    .collect(Collectors.toList()))
            .build());
}
