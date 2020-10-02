package org.example.beatmybet.controller;


import org.example.beatmybet.dto.CategoryDTO;
import org.example.beatmybet.service.CategoryService;
import org.example.beatmybet.web.ApiResponse;
import org.example.beatmybet.web.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/main")
    public ApiResponse<List<CategoryDTO>> getMainCategories() {
        return Responses.okResponse(categoryService.getMainCategories());
    }

    @GetMapping("/subcategories/{id}")
    public ApiResponse<List<CategoryDTO>> getSubcategories(@PathVariable("id") Long id) {
        return Responses.okResponse(categoryService.getSubcategories(id));

    }

    @GetMapping("{id}")
    public ApiResponse<CategoryDTO> getById(@PathVariable("id") Long id){
        return Responses.okResponse(categoryService.getById(id));
    }
}
