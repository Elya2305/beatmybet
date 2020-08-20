package org.example.beatmybet.controller;


import org.example.beatmybet.dto.CategoryDTO;
import org.example.beatmybet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/main")
    public List<CategoryDTO> getMainCategories() {
        return categoryService.getMainCategories();

    }

    @GetMapping("/subcategories/{id}")
    public List<CategoryDTO> getSubcategories(@PathVariable("id") Long id) {
        return categoryService.getSubcategories(id);

    }

    @GetMapping("{id}")
    public CategoryDTO getById(@PathVariable("id") Long id){
        return categoryService.getById(id);

    }
}