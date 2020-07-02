package org.example.beatmybet.controllers;


import org.example.beatmybet.entity.Category;
import org.example.beatmybet.dto.CategoryDTO;
import org.example.beatmybet.exceptions.TypeNotFoundException;
import org.example.beatmybet.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired CategoryRepository categoryRepository;

    @GetMapping("/all")
    public Set<CategoryDTO> getAllCategories(){
        return (categoryRepository.findAll()
                .stream()
                .map(mapToCategoryDTO).collect(toSet()));
    }

    @GetMapping("/main")
    public Set<CategoryDTO> getMainCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(o -> o.getCategory() == null)
                .map(mapToCategoryDTO).collect(toSet());
    }

    @GetMapping("/subcategories/{id}")
    public Set<CategoryDTO> getSubcategories(@PathVariable("id") Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException(id))
                .getSubCategories()
                .stream()
                .map(mapToCategoryDTO).collect(toSet());
    }

    @GetMapping("{id}")
    public CategoryDTO getById(@PathVariable("id") Long id){
        return categoryRepository.findById(id)
                .map(mapToCategoryDTO)
                .orElseThrow(() -> new TypeNotFoundException(id));
    }

    Function<Category, CategoryDTO> mapToCategoryDTO = (category -> CategoryDTO.builder()
            .id(category.getId()).name(category.getName()).build());
}
