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

    @GetMapping
    public Map<String, Object> getReport() {

        return reportService.generateReport();
    }
}