package com.work.backend_report.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public void generateExcelReport(HttpServletResponse response, @RequestParam(required = true) String whenToSummary)
            throws Exception {

        response.setContentType("application/octet-stream");

        LocalDate startDate = null;
        LocalDate endDate = null;

        if (whenToSummary.equalsIgnoreCase("weekly")) {

            startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            endDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

            logger.info("Weekly summary from {} to {}", startDate, endDate);

        } else if ("monthly".equalsIgnoreCase(whenToSummary)) {
            // Monthly: From the 26th of the previous month to the 25th of the current month
            LocalDate now = LocalDate.now();
            if (now.getDayOfMonth() >= 26) {
                startDate = now.withDayOfMonth(26).minusMonths(1);
                endDate = now.withDayOfMonth(25);
            } else {
                startDate = now.withDayOfMonth(26).minusMonths(2);
                endDate = now.withDayOfMonth(25).minusMonths(1);
            }

            logger.info("Monthly summary from {} to {}", startDate, endDate);
        } else {
            throw new IllegalArgumentException("Invalid 'whenToSummary' value. Use 'weekly' or 'monthly'.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String generateDate = LocalDateTime.now().format(formatter);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=reportsummary-" + generateDate + ".xls";

        response.setHeader(headerKey, headerValue);

        logger.info("Generate an Excel file for date range: {} to {}", startDate, endDate);

        reportGeneratorService.generateExcel(response, startDate, endDate);

        response.flushBuffer();
    }

}
