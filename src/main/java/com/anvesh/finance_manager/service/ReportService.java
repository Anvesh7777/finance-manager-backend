package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.entity.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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
                transactionService.getAllTransactions();

        Map<String, Double> categoryIncome =
                new HashMap<>();

        Map<String, Double> categoryExpense =
                new HashMap<>();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {

            LocalDate date =
                    transaction.getDate();

            if (
                    date.getYear() == year
                            &&
                            date.getMonthValue() == month
            ) {

                String category =
                        transaction.getCategory()
                                .getName();

                String type =
                        transaction.getCategory()
                                .getType();

                double amount =
                        transaction.getAmount();

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

                } else {

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

        report.put("month", month);

        report.put("year", year);

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

        // CATEGORY BREAKDOWN
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

    // YEARLY REPORT
    public Map<String, Object> generateYearlyReport(
            int year
    ) {

        List<Transaction> transactions =
                transactionService.getAllTransactions();

        Map<String, Double> categoryIncome =
                new HashMap<>();

        Map<String, Double> categoryExpense =
                new HashMap<>();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {

            LocalDate date =
                    transaction.getDate();

            if (
                    date.getYear() == year
            ) {

                String category =
                        transaction.getCategory()
                                .getName();

                String type =
                        transaction.getCategory()
                                .getType();

                double amount =
                        transaction.getAmount();

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

                } else {

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

        report.put("year", year);

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

        // CATEGORY BREAKDOWN
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
}