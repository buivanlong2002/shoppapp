package com.example.shopapp.mapper;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDTO categoryDTO);
    void updateCategory(@MappingTarget Category category, CategoryDTO categoryDTO);
}
