package com.work.backend_report.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.backend_report.service.ReportGeneratorService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/excel")
@RestController

public class ExcelReportController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelReportController.class);

    private final ReportGeneratorService reportGeneratorService;

    @Autowired
    public ExcelReportController(ReportGeneratorService reportGeneratorService) {
        this.reportGeneratorService = reportGeneratorService;
    }

    @GetMapping("/write")
    public void generateExcelReport(HttpServletResponse response) throws Exception {

        response.setContentType("application/octet-stream");

        // Excel file will be generated and saved to C:\Users\admin\Downloads as
        // 'employee.xls'

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String generateDate = LocalDateTime.now().format(formatter);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=reportsummary-" + generateDate +".xls";

        response.setHeader(headerKey, headerValue);

        logger.info("Generate an Excel file");
        reportGeneratorService.generateExcel(response);

        response.flushBuffer();
    }

}
