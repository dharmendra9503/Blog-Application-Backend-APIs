package com.example.blogapis.service.implementation;

import com.example.blogapis.exception.ResourceNotFoundException;
import com.example.blogapis.model.Category;
import com.example.blogapis.payloads.CategoryDataTransfer;
import com.example.blogapis.repository.CategoryRepository;
import com.example.blogapis.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDataTransfer createCategory(CategoryDataTransfer categoryDTO) {

        Category category = this.modelMapper.map(categoryDTO, Category.class);   //This will convert CategoryDataTransfer object to Category object.
        Category saved = categoryRepository.save(category);

        return modelMapper.map(saved, CategoryDataTransfer.class);
    }


    @Override
    public List<CategoryDataTransfer> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryDataTransfer> categoryDTO = categories.stream().map((category) -> modelMapper.map(category, CategoryDataTransfer.class)).toList();

        return categoryDTO;
    }

    @Override
    public CategoryDataTransfer getCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        return modelMapper.map(category, CategoryDataTransfer.class);
    }

    @Override
    public CategoryDataTransfer updateCategory(CategoryDataTransfer categoryDTO, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        Category updated = categoryRepository.save(category);

        return modelMapper.map(updated, CategoryDataTransfer.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        categoryRepository.delete(category);
    }
}
