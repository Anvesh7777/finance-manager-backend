package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.entity.Category;
import com.anvesh.finance_manager.entity.User;

import com.anvesh.finance_manager.repository.CategoryRepository;
import com.anvesh.finance_manager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));
    }

    // CREATE CATEGORY
    public Category createCategory(
            Category category
    ) {

        User currentUser = getCurrentUser();

        // DUPLICATE CHECK
        if (categoryRepository
                .findByNameAndUser(
                        category.getName(),
                        currentUser
                ).isPresent()) {

            throw new RuntimeException(
                    "Category already exists"
            );
        }

        category.setUser(currentUser);

        return categoryRepository
                .save(category);
    }

    // GET ALL USER + DEFAULT CATEGORIES
    public List<Category> getAllCategories() {

        User currentUser = getCurrentUser();

        List<Category> categories =
                new ArrayList<>();

        // DEFAULT CATEGORIES
        categories.addAll(
                categoryRepository
                        .findByIsDefaultCategoryTrue()
        );

        // USER CUSTOM CATEGORIES
        categories.addAll(
                categoryRepository
                        .findByUser(currentUser)
        );

        return categories;
    }
}