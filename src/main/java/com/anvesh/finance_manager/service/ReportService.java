package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.entity.Transaction;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private TransactionService transactionService;

    // MONTHLY REPORT
    public Map<String, Object> generateMonthlyReport(

            int year,

            int month
    ) {

        List<Transaction> transactions =
                transactionService
                        .getAllTransactions();

        return buildReport(

                transactions,

                transaction ->

                        transaction.getDate()
                                .getYear() == year
                                &&

                                transaction.getDate()
                                        .getMonthValue() == month,

                month,

                year,

                true
        );
    }

    // YEARLY REPORT
    public Map<String, Object> generateYearlyReport(
            int year
    ) {

        List<Transaction> transactions =
                transactionService
                        .getAllTransactions();

        return buildReport(

                transactions,

                transaction ->

                        transaction.getDate()
                                .getYear() == year,

                null,

                year,

                false
        );
    }

    // REPORT BUILDER
    private Map<String, Object> buildReport(

            List<Transaction> transactions,

            TransactionFilter filter,

            Integer month,

            Integer year,

            boolean includeMonth
    ) {

        Map<String, Double> categoryIncome =
                new HashMap<>();

        Map<String, Double> categoryExpense =
                new HashMap<>();

        double totalIncome = 0;

        double totalExpense = 0;

        for (
                Transaction transaction
                : transactions
        ) {

            // NULL SAFETY
            if (
                    transaction.getDate() == null
                            ||

                            transaction.getCategory() == null
            ) {

                continue;
            }

            if (
                    filter.matches(transaction)
            ) {

                String category =
                        transaction
                                .getCategory()
                                .getName();

                String type =
                        transaction
                                .getCategory()
                                .getType();

                double amount =
                        transaction.getAmount();

                // INCOME
                if (
                        type.equalsIgnoreCase(
                                "INCOME"
                        )
                ) {

                    totalIncome += amount;

                    categoryIncome.put(

                            category,

                            categoryIncome.getOrDefault(
                                    category,
                                    0.0
                            ) + amount
                    );

                }

                // EXPENSE
                else {

                    totalExpense += amount;

                    categoryExpense.put(

                            category,

                            categoryExpense.getOrDefault(
                                    category,
                                    0.0
                            ) + amount
                    );
                }
            }
        }

        Map<String, Object> report =
                new HashMap<>();

        // OPTIONAL MONTH
        if (
                includeMonth
        ) {

            report.put(
                    "month",
                    month
            );
        }

        report.put(
                "year",
                year
        );

        // TOTALS
        report.put(
                "totalIncome",
                totalIncome
        );

        report.put(
                "totalExpense",
                totalExpense
        );

        report.put(
                "netSavings",
                totalIncome - totalExpense
        );

        // BREAKDOWN
        report.put(
                "incomeBreakdown",
                categoryIncome
        );

        report.put(
                "expenseBreakdown",
                categoryExpense
        );

        return report;
    }

    // FUNCTIONAL INTERFACE
    private interface TransactionFilter {

        boolean matches(
                Transaction transaction
        );
    }
}