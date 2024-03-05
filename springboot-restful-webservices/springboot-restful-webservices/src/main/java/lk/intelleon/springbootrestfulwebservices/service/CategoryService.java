package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.CategoryDTO;
import lk.intelleon.springbootrestfulwebservices.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    void saveCategory(CategoryDTO categoryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    List<CategoryDTO> getAllCategory();

    CategoryEntity getCategoryById(Long categoryId);
}
