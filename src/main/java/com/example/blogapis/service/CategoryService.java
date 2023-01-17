package com.example.blogapis.service;

import com.example.blogapis.payloads.CategoryDataTransfer;
import com.example.blogapis.payloads.CategoryResponse;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDataTransfer createCategory(CategoryDataTransfer categoryDTO);

    //get all
    CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize);

    //get
    CategoryDataTransfer getCategory(Integer categoryId);

    //update
    CategoryDataTransfer updateCategory(CategoryDataTransfer categoryDTO, Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId);

}