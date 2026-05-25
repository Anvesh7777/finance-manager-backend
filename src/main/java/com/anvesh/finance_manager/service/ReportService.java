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
                transactionService
                        .getAllTransactions();

        Map<String, Double> incomeMap =
                new HashMap<>();

        Map<String, Double> expenseMap =
                new HashMap<>();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {

            LocalDate date =
                    transaction.getDate();

            if (date.getYear() == year
                    &&
                    date.getMonthValue() == month) {

                String category =
                        transaction.getCategory()
                                .getName();

                String type =
                        transaction.getCategory()
                                .getType();

                double amount =
                        transaction.getAmount();

                if (type.equalsIgnoreCase("INCOME")) {

                    incomeMap.put(
                            category,
                            incomeMap.getOrDefault(
                                    category,
                                    0.0
                            ) + amount
                    );

                    totalIncome += amount;

                } else {

                    expenseMap.put(
                            category,
                            expenseMap.getOrDefault(
                                    category,
                                    0.0
                            ) + amount
                    );

                    totalExpense += amount;
                }
            }
        }

        Map<String, Object> report =
                new HashMap<>();

        report.put("month", month);

        report.put("year", year);

        report.put(
                "totalIncome",
                incomeMap
        );

        report.put(
                "totalExpenses",
                expenseMap
        );

        report.put(
                "netSavings",
                totalIncome - totalExpense
        );

        return report;
    }

    // YEARLY REPORT
    public Map<String, Object> generateYearlyReport(
            int year
    ) {

        List<Transaction> transactions =
                transactionService
                        .getAllTransactions();

        Map<String, Double> incomeMap =
                new HashMap<>();

        Map<String, Double> expenseMap =
                new HashMap<>();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {

            LocalDate date =
                    transaction.getDate();

            if (date.getYear() == year) {

                String category =
                        transaction.getCategory()
                                .getName();

                String type =
                        transaction.getCategory()
                                .getType();

                double amount =
                        transaction.getAmount();

                if (type.equalsIgnoreCase("INCOME")) {

                    incomeMap.put(
                            category,
                            incomeMap.getOrDefault(
                                    category,
                                    0.0
                            ) + amount
                    );

                    totalIncome += amount;

                } else {

                    expenseMap.put(
                            category,
                            expenseMap.getOrDefault(
                                    category,
                                    0.0
                            ) + amount
                    );

                    totalExpense += amount;
                }
            }
        }

        Map<String, Object> report =
                new HashMap<>();

        report.put("year", year);

        report.put(
                "totalIncome",
                incomeMap
        );

        report.put(
                "totalExpenses",
                expenseMap
        );

        report.put(
                "netSavings",
                totalIncome - totalExpense
        );

        return report;
    }
}