package com.example.shopapp.service;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.mapper.CategoryMapper;
import com.example.shopapp.model.Category;
import com.example.shopapp.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    public Category updateCategory(Long id , CategoryDTO categoryDTO) {
        Category category = findById(id);
        categoryMapper.updateCategory(category,categoryDTO);
        categoryRepository.save(category);
        return category;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public Category save( CategoryDTO category) {
        Category newCategory = categoryMapper.toCategory(category);
        return Collections.singletonList(categoryRepository.save(newCategory)).get(0);
    }
    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }
}
