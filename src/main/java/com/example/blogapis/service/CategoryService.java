package com.example.blogapis.service;

import com.example.blogapis.payloads.CategoryDataTransfer;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDataTransfer createCategory(CategoryDataTransfer categoryDTO);

    //get all
    List<CategoryDataTransfer> getAllCategory();

    //get
    CategoryDataTransfer getCategory(Integer categoryId);

    //update
    CategoryDataTransfer updateCategory(CategoryDataTransfer categoryDTO, Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId);

}