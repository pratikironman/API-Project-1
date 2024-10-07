package com.ashu.blogapp.Controllers;

import com.ashu.blogapp.Payloads.APIResponse;
import com.ashu.blogapp.Payloads.CategoryDto;
import com.ashu.blogapp.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //POST - create User
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCatory(@Valid  @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }


    //PUT - update User
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("catId") Integer categoryId){
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);

        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
    }


    //DELETE - delete User
    @DeleteMapping("/{catId}")

    //We could also use "public void" if we don't want to return anything from the method, but we are returning here something, so we used "public ResponseEntity nd using APIResponse class we created for various Responses we get"
     public ResponseEntity<APIResponse> deleteCategory (@PathVariable("catId") Integer categoryId){
        this.categoryService.deleteCategory(categoryId);

        return new ResponseEntity<APIResponse>(new APIResponse("Category Deleted Successfully !!!", true), HttpStatus.OK);
    }

    //GET - get User
    //getting particular user based on Id
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("catId") Integer categoryId){
        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);

        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    // getting all Users
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
         List<CategoryDto> categoryDtos = this.categoryService.getAllCategories();

         return ResponseEntity.ok(categoryDtos);
//        return new ResponseEntity<List<CategoryDto>>(categoryDtos, HttpStatus.OK);
    }

}
