package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private TransactionService transactionService;

    public Map<String, Object> generateReport() {

        List<Transaction> transactions =
                transactionService.getAllTransactions();

        double income = 0;
        double expense = 0;

        for (Transaction t : transactions) {

            if (
                    t.getCategory()
                            .getType()
                            .equals("INCOME")
            ) {

                income += t.getAmount();

            } else {

                expense += t.getAmount();
            }
        }

        Map<String, Object> report =
                new HashMap<>();

        report.put(
                "totalIncome",
                income
        );

        report.put(
                "totalExpense",
                expense
        );

        report.put(
                "netSavings",
                income - expense
        );

        return report;
    }
}