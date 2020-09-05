package org.example.beatmybet.service.impl;

import org.example.beatmybet.dto.CategoryDTO;
import org.example.beatmybet.entity.Category;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.CategoryRepository;
import org.example.beatmybet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getMainCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(o -> o.getCategory() == null)
                .map(mapToCategoryDTO)
                .collect(toList());
    }

    public List<CategoryDTO> getSubcategories(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category", id))
                .getSubCategories()
                .stream()
                .map(mapToCategoryDTO)
                .collect(toList());
    }

    public CategoryDTO getById(Long id) {
        return categoryRepository.findById(id)
                .map(mapToCategoryDTO)
                .orElseThrow(() -> new NotFoundException("category", id));
    }

    Function<Category, CategoryDTO> mapToCategoryDTO = (category -> CategoryDTO.builder()
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
