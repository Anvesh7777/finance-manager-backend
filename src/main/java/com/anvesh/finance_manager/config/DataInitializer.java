package com.anvesh.finance_manager.config;

import com.anvesh.finance_manager.entity.Category;
import com.anvesh.finance_manager.repository.CategoryRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            CategoryRepository categoryRepository
    ) {

        return args -> {

            // ONLY INSERT IF EMPTY
            if (
                    categoryRepository.count() == 0
            ) {

                createCategory(
                        categoryRepository,
                        "Salary",
                        "INCOME"
                );

                createCategory(
                        categoryRepository,
                        "Freelance",
                        "INCOME"
                );

                createCategory(
                        categoryRepository,
                        "Food",
                        "EXPENSE"
                );

                createCategory(
                        categoryRepository,
                        "Rent",
                        "EXPENSE"
                );

                createCategory(
                        categoryRepository,
                        "Entertainment",
                        "EXPENSE"
                );

                createCategory(
                        categoryRepository,
                        "Transport",
                        "EXPENSE"
                );

                System.out.println(
                        "Default categories initialized successfully."
                );
            }
        };
    }

    // HELPER METHOD
    private void createCategory(

            CategoryRepository categoryRepository,

            String name,

            String type
    ) {

        Category category =
                new Category();

        category.setName(name);

        category.setType(type);

        categoryRepository.save(category);
    }
}