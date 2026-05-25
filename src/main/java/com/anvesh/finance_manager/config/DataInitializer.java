package com.anvesh.finance_manager.config;

import com.anvesh.finance_manager.entity.Category;

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

            if (
                    categoryRepository.count() == 0
            ) {

                Category salary =
                        new Category();

                salary.setName(
                        "Salary"
                );

                salary.setType(
                        "INCOME"
                );

                categoryRepository.save(
                        salary
                );

                Category freelance =
                        new Category();

                freelance.setName(
                        "Freelance"
                );

                freelance.setType(
                        "INCOME"
                );

                categoryRepository.save(
                        freelance
                );

                Category food =
                        new Category();

                food.setName(
                        "Food"
                );

                food.setType(
                        "EXPENSE"
                );

                categoryRepository.save(
                        food
                );

                Category rent =
                        new Category();

                rent.setName(
                        "Rent"
                );

                rent.setType(
                        "EXPENSE"
                );

                categoryRepository.save(
                        rent
                );

                Category entertainment =
                        new Category();

                entertainment.setName(
                        "Entertainment"
                );

                entertainment.setType(
                        "EXPENSE"
                );

                categoryRepository.save(
                        entertainment
                );

                Category transport =
                        new Category();

                transport.setName(
                        "Transport"
                );

                transport.setType(
                        "EXPENSE"
                );

                categoryRepository.save(
                        transport
                );
            }
        };
    }
}