package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.entity.Category;
import com.anvesh.finance_manager.entity.User;

import com.anvesh.finance_manager.repository.CategoryRepository;
import com.anvesh.finance_manager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // GET CURRENT USER
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->

                        new RuntimeException(
                                "User not found"
                        )
                );
    }

    // CREATE CATEGORY
    public Category createCategory(
            Category category
    ) {

        User currentUser =
                getCurrentUser();

        // VALIDATION
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

        // DUPLICATE CHECK
        if (
                categoryRepository
                        .existsByNameIgnoreCaseAndUser(
                                category.getName(),
                                currentUser
                        )
        ) {

            throw new RuntimeException(
                    "Category already exists"
            );
        }

        category.setUser(currentUser);

        return categoryRepository
                .save(category);
    }

    // GET ALL USER CATEGORIES
    public List<Category> getAllCategories() {

        User currentUser =
                getCurrentUser();

        return categoryRepository
                .findByUser(currentUser);
    }

    // GET CATEGORY BY ID
    public Category getCategoryById(
            Long id
    ) {

        return categoryRepository
                .findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Category not found"
                        )
                );
    }
}