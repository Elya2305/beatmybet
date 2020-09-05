package org.example.beatmybet.service;

import org.example.beatmybet.dto.CategoryDTO;

import java.io.Serializable;
import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getMainCategories();

    List<CategoryDTO> getSubcategories(Long id);

    CategoryDTO getById(Long id);

}
