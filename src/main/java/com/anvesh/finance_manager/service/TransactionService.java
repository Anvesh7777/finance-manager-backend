package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.entity.Transaction;
import com.anvesh.finance_manager.entity.User;

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

    // GET CURRENT LOGGED IN USER
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
                        ));
    }

    // CREATE TRANSACTION
    public Transaction createTransaction(
            Transaction transaction
    ) {

        LocalDate indiaToday =
                LocalDate.now(
                        ZoneId.of("Asia/Kolkata")
                );

        // FUTURE DATE VALIDATION
        if (
                transaction.getDate()
                        .isAfter(indiaToday)
        ) {

            throw new RuntimeException(
                    "Future date is not allowed"
            );
        }

        User currentUser =
                getCurrentUser();

        transaction.setUser(
                currentUser
        );

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

    // FILTER TRANSACTIONS BY DATE RANGE
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
                                ));

        // SECURITY CHECK
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

        User currentUser =
                getCurrentUser();

        Transaction transaction =
                transactionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Transaction not found"
                                ));

        // SECURITY CHECK
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

        LocalDate indiaToday =
                LocalDate.now(
                        ZoneId.of("Asia/Kolkata")
                );

        // DATE VALIDATION
        if (
                updatedTransaction.getDate()
                        .isAfter(indiaToday)
        ) {

            throw new RuntimeException(
                    "Future date is not allowed"
            );
        }

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
                updatedTransaction.getCategory()
        );

        return transactionRepository
                .save(transaction);
    }
}