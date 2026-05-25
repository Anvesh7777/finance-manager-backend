package com.anvesh.finance_manager.repository;

import com.anvesh.finance_manager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}