package com.ashu.blogapp.Services;

import com.ashu.blogapp.Entity.Category;
import com.ashu.blogapp.Payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    // in interface any method created is by default "public" method
    // we use interfaces for "loose coupling" & unit testing purpose so that whenever we need, we could make changed in any of method we want

    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId);

    //get
    CategoryDto getCategoryById(Integer categoryId);

    //getAll
    List<CategoryDto> getAllCategories();

}
