package lk.intelleon.springbootrestfulwebservices.service.impl;

import lk.intelleon.springbootrestfulwebservices.entity.CategoryEntity;
import lk.intelleon.springbootrestfulwebservices.entity.UnitEntity;
import lk.intelleon.springbootrestfulwebservices.util.Convertor;
import lk.intelleon.springbootrestfulwebservices.dto.CategoryDTO;
import lk.intelleon.springbootrestfulwebservices.repo.CategoryRepository;
import lk.intelleon.springbootrestfulwebservices.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repository;

    @Autowired
    Convertor convertor;

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        repository.save(convertor.CategoryDtoToCategoryEntity(categoryDTO));
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = repository.findById(categoryDTO.getId()).get();
        categoryEntity.setCode(categoryDTO.getCode());
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setStatus(categoryDTO.getStatus());
        repository.save(categoryEntity);
    }

    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryEntity> all = repository.findAll();
        return convertor.CategoryEntityListToCategoryDTOList(all);
    }

    @Override
    public CategoryEntity getCategoryById(Long categoryId) {
        return repository.findById(categoryId).orElse(null);
    }
}
