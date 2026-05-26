package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.entity.Category;
import com.anvesh.finance_manager.entity.Transaction;
import com.anvesh.finance_manager.entity.User;

import com.anvesh.finance_manager.repository.CategoryRepository;
import com.anvesh.finance_manager.repository.TransactionRepository;
import com.anvesh.finance_manager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    // VALIDATE TRANSACTION
    private void validateTransaction(
            Transaction transaction
    ) {

        if (
                transaction.getAmount() == null
                        || transaction.getAmount() <= 0
        ) {

            throw new RuntimeException(
                    "Amount must be greater than 0"
            );
        }

        if (
                transaction.getDate() == null
        ) {

            throw new RuntimeException(
                    "Transaction date is required"
            );
        }

        if (
                transaction.getDescription() == null
                        || transaction.getDescription()
                        .trim()
                        .isEmpty()
        ) {

            throw new RuntimeException(
                    "Description is required"
            );
        }

        if (
                transaction.getCategory() == null
                        || transaction.getCategory().getId() == null
        ) {

            throw new RuntimeException(
                    "Category is required"
            );
        }

        LocalDate indiaToday =
                LocalDate.now(
                        ZoneId.of("Asia/Kolkata")
                );

        // FUTURE DATE CHECK
        if (
                transaction.getDate()
                        .isAfter(indiaToday)
        ) {

            throw new RuntimeException(
                    "Future date is not allowed"
            );
        }
    }

    // CREATE TRANSACTION
    public Transaction createTransaction(
            Transaction transaction
    ) {

        validateTransaction(transaction);

        User currentUser =
                getCurrentUser();

        Category category =
                categoryRepository
                        .findById(
                                transaction
                                        .getCategory()
                                        .getId()
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Category not found"
                                )
                        );

        transaction.setUser(currentUser);

        transaction.setCategory(category);

        return transactionRepository
                .save(transaction);
    }

    // GET ALL USER TRANSACTIONS
    public List<Transaction> getAllTransactions() {

        User currentUser =
                getCurrentUser();

        return transactionRepository
                .findByUserOrderByDateDesc(
                        currentUser
                );
    }

    // FILTER TRANSACTIONS
    public List<Transaction>
    getTransactionsByDateRange(

            LocalDate startDate,

            LocalDate endDate
    ) {

        User currentUser =
                getCurrentUser();

        return transactionRepository
                .findByUserAndDateBetweenOrderByDateDesc(

                        currentUser,

                        startDate,

                        endDate
                );
    }

    // DELETE TRANSACTION
    public void deleteTransaction(
            Long id
    ) {

        User currentUser =
                getCurrentUser();

        Transaction transaction =
                transactionRepository
                        .findById(id)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Transaction not found"
                                )
                        );

        // OWNERSHIP CHECK
        if (
                !transaction.getUser()
                        .getId()
                        .equals(
                                currentUser.getId()
                        )
        ) {

            throw new RuntimeException(
                    "Access denied"
            );
        }

        transactionRepository
                .delete(transaction);
    }

    // UPDATE TRANSACTION
    public Transaction updateTransaction(

            Long id,

            Transaction updatedTransaction
    ) {

        validateTransaction(
                updatedTransaction
        );

        User currentUser =
                getCurrentUser();

        Transaction transaction =
                transactionRepository
                        .findById(id)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Transaction not found"
                                )
                        );

        // OWNERSHIP CHECK
        if (
                !transaction.getUser()
                        .getId()
                        .equals(
                                currentUser.getId()
                        )
        ) {

            throw new RuntimeException(
                    "Access denied"
            );
        }

        Category category =
                categoryRepository
                        .findById(
                                updatedTransaction
                                        .getCategory()
                                        .getId()
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Category not found"
                                )
                        );

        transaction.setAmount(
                updatedTransaction.getAmount()
        );

        transaction.setDate(
                updatedTransaction.getDate()
        );

        transaction.setDescription(
                updatedTransaction.getDescription()
        );

        transaction.setCategory(
                category
        );

        return transactionRepository
                .save(transaction);
    }
}