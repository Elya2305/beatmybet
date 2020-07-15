package org.example.beatmybet.controllers;


import org.example.beatmybet.dto.CategoryDTO;
import org.example.beatmybet.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired CategoryService categoryService;

    @GetMapping("/all")
    public Set<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/main")
    public Set<CategoryDTO> getMainCategories() {
        return categoryService.getMainCategories();

    }

    @GetMapping("/subcategories/{id}")
    public Set<CategoryDTO> getSubcategories(@PathVariable("id") Long id) {
        return categoryService.getSubcategories(id);

    }

    @GetMapping("{id}")
    public CategoryDTO getById(@PathVariable("id") Long id){
        return categoryService.getById(id);

    }
}
