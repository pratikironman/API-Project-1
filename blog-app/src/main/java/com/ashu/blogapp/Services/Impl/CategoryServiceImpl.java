package com.ashu.blogapp.Services.Impl;

import com.ashu.blogapp.Entity.Category;
import com.ashu.blogapp.Exceptions.ResourceNotFoundException;
import com.ashu.blogapp.Payloads.CategoryDto;
import com.ashu.blogapp.Repository.CategoryRepo;
import com.ashu.blogapp.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    // conversion of "Category" to "CategoryDto" & vice-versa using ModelMappers Library
    // 1st add modelmapper dependency
    // Declare a bean of ModelMapper... then by Autowiring we can use it for conversion purpose
    @Autowired
    private ModelMapper modelMapper;

    // conversion is made directly into methods below

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        //conversion using ModelMapper
        Category category = this.modelMapper.map(categoryDto, Category.class);

        Category addedCategory = this.categoryRepo.save(category);

        return this.modelMapper.map(addedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCat = this.categoryRepo.save(cat);

        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> category = this.categoryRepo.findAll();

        // we cant directy return category as returning type of method is categoryDto, so we need to convert using stream map library
        List<CategoryDto> categoryDto = category.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());

        return categoryDto;
    }
}
