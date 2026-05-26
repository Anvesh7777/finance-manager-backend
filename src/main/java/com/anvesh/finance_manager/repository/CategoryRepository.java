package com.anvesh.finance_manager.repository;

import com.anvesh.finance_manager.entity.Category;
import com.anvesh.finance_manager.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {

    // USER SPECIFIC CATEGORIES
    List<Category> findByUser(User user);

    // DEFAULT CATEGORIES
    List<Category> findByDefaultCategoryTrue();

    // DUPLICATE CHECK
    Optional<Category> findByNameAndUser(
            String name,
            User user
    );
}