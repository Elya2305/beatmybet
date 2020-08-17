package org.example.beatmybet.service;

import org.example.beatmybet.dto.CategoryDTO;
import org.example.beatmybet.dto.SubCategoryDto;
import org.example.beatmybet.entity.Category;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Set<Serializable> getAllCategoriesForHomePage() {
        return categoryRepository.findAll()
                .stream()
                .filter(o -> o.getCategory() == null)
                .map(mapToCategoryDTO)
                .collect(Collectors.toSet());
    }

    public Set<CategoryDTO> getAllCategoriesForHomePage2() {
        return categoryRepository.findAll()
                .stream()
                .filter(o -> o.getCategory() == null)
                .map(mapToCategoryDTO)
                .collect(Collectors.toSet());
    }

    public Set<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(o -> o.getCategory() == null)
                .map(mapToCategoryDTO)
                .collect(Collectors.toSet());
    }

    public Set<CategoryDTO> getMainCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(o -> o.getCategory() == null)
                .map(mapToCategoryDTO).collect(toSet());
    }

    public Set<CategoryDTO> getSubcategories(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category", id))
                .getSubCategories()
                .stream()
                .map(mapToCategoryDTO).collect(toSet());
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
                    .map(subCat -> SubCategoryDto.builder()
                            .id(subCat.getId())
                            .name(subCat.getName())
                            .build()).collect(Collectors.toSet()))
            .build());
}
