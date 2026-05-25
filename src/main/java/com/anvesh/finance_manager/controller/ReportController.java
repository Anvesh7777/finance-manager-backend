package com.anvesh.finance_manager.controller;

import com.anvesh.finance_manager.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        return reportService
                .generateYearlyReport(year);
    }
}