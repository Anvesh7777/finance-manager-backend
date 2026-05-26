package com.anvesh.finance_manager.controller;

import com.anvesh.finance_manager.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // MONTHLY REPORT
    @GetMapping("/monthly/{year}/{month}")
    public Map<String, Object> getMonthlyReport(

            @PathVariable int year,

            @PathVariable int month
    ) {

        validateYear(year);

        validateMonth(month);

        return reportService
                .generateMonthlyReport(
                        year,
                        month
                );
    }

    // YEARLY REPORT
    @GetMapping("/yearly/{year}")
    public Map<String, Object> getYearlyReport(

            @PathVariable int year
    ) {

        validateYear(year);

        return reportService
                .generateYearlyReport(year);
    }

    // VALIDATE MONTH
    private void validateMonth(
            int month
    ) {

        if (
                month < 1 ||
                        month > 12
        ) {

            throw new RuntimeException(
                    "Invalid month"
            );
        }
    }

    // VALIDATE YEAR
    private void validateYear(
            int year
    ) {

        int currentYear =
                Year.now().getValue();

        if (
                year < 2000 ||
                        year > currentYear + 5
        ) {

            throw new RuntimeException(
                    "Invalid year"
            );
        }
    }
}