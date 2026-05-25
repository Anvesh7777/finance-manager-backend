package com.anvesh.finance_manager.controller;

import com.anvesh.finance_manager.entity.Transaction;
import com.anvesh.finance_manager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public Map<String, Object> getReport() {

        List<Transaction> transactions = transactionService.getAllTransactions();

        double income = 0;
        double expense = 0;

        for (Transaction t : transactions) {

            if (t.getCategory().getType().equals("INCOME")) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
        }

        Map<String, Object> report = new HashMap<>();

        report.put("totalIncome", income);
        report.put("totalExpense", expense);
        report.put("netSavings", income - expense);

        return report;
    }
}