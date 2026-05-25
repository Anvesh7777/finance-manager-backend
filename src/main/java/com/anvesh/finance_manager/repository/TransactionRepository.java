package com.anvesh.finance_manager.repository;

import com.anvesh.finance_manager.entity.Transaction;
import com.anvesh.finance_manager.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    // USER SPECIFIC TRANSACTIONS
    List<Transaction> findByUser(User user);

    // DATE RANGE FILTER
    List<Transaction> findByUserAndDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );

    // CATEGORY FILTER
    List<Transaction> findByUserAndCategory_Name(
            User user,
            String categoryName
    );

    // MONTHLY REPORT
    List<Transaction> findByUserAndDateBetweenOrderByDateDesc(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );
}