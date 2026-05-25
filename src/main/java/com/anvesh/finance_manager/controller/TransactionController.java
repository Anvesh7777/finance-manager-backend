package com.anvesh.finance_manager.controller;

import com.anvesh.finance_manager.entity.Transaction;
import com.anvesh.finance_manager.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // CREATE TRANSACTION
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(
            @RequestBody Transaction transaction
    ) {

        return transactionService
                .createTransaction(transaction);
    }

    // GET ALL USER TRANSACTIONS
    @GetMapping
    public List<Transaction> getAllTransactions() {

        return transactionService
                .getAllTransactions();
    }

    // FILTER TRANSACTIONS BY DATE
    @GetMapping("/filter")
    public List<Transaction> filterTransactionsByDate(

            @RequestParam
            @DateTimeFormat(iso =
                    DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso =
                    DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {

        return transactionService
                .getTransactionsByDateRange(
                        startDate,
                        endDate
                );
    }

    // DELETE TRANSACTION
    @DeleteMapping("/{id}")
    public String deleteTransaction(
            @PathVariable Long id
    ) {

        transactionService
                .deleteTransaction(id);

        return "Transaction deleted successfully";
    }

    // UPDATE TRANSACTION
    @PutMapping("/{id}")
    public Transaction updateTransaction(

            @PathVariable Long id,

            @RequestBody Transaction transaction
    ) {

        return transactionService
                .updateTransaction(id, transaction);
    }
}