package com.example.blogapis.controller;

import com.example.blogapis.payloads.CategoryDataTransfer;
import com.example.blogapis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/API")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDataTransfer> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping("/category/{id}")
    public CategoryDataTransfer getCategory(@PathVariable Integer id){
        return categoryService.getCategory(id);
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryDataTransfer> createCategory(@Valid @RequestBody CategoryDataTransfer categoryDTO){
        CategoryDataTransfer created = categoryService.createCategory(categoryDTO);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryDataTransfer> updateCategory(@Valid @RequestBody CategoryDataTransfer categoryDTO, @PathVariable Integer id){
        CategoryDataTransfer updated = categoryService.updateCategory(categoryDTO, id);

        return new ResponseEntity<>(updated, HttpStatus.CREATED);
    }

    @DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);

        return "Category with id "+id+" deleted successfully";
    }
}
