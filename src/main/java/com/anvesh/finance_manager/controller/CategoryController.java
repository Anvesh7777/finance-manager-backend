package com.anvesh.finance_manager.controller;

import com.anvesh.finance_manager.entity.Category;
import com.anvesh.finance_manager.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // CREATE CATEGORY
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(
            @RequestBody Category category
    ) {

        // BASIC VALIDATION
        if (
                category.getName() == null
                        || category.getName().trim().isEmpty()
        ) {

            throw new RuntimeException(
                    "Category name is required"
            );
        }

        if (
                category.getType() == null
                        || category.getType().trim().isEmpty()
        ) {

            throw new RuntimeException(
                    "Category type is required"
            );
        }

        return categoryService
                .createCategory(category);
    }

    // GET ALL CATEGORIES
    @GetMapping
    public List<Category> getAllCategories() {

        return categoryService
                .getAllCategories();
    }

    // GET CATEGORY BY ID
    @GetMapping("/{id}")
    public Category getCategoryById(
            @PathVariable Long id
    ) {

        return categoryService
                .getCategoryById(id);
    }
}