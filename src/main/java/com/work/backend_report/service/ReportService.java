/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.work.backend_report.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.backend_report.entity.Report;
import com.work.backend_report.enumeration.Project;
import com.work.backend_report.record.ReportSummary;
import com.work.backend_report.repository.ReportRepository;

/**
 *
 * @author ferdi
 */
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(String partnerName, Project project, String notes) {
        // Set current time in UTC
        Instant now = Instant.now();
        Report report = new Report(
                null,
                partnerName,
                0,
                new Date(), // Current Date
                null, // Current Time (UTC)
                null, // Ending hour can be set later
                project,
                notes
        );

        report.setStartingHour(now);  // Convert and store in Asia/Jakarta timezone
        return reportRepository.save(report);  // Persist the report in the database
    }

    public Report endReport(Report report, int supportTime) {
        Instant now = Instant.now();  // Current UTC time
        report.setEndingHour(now);    // Convert to Asia/Jakarta and store in local time
        report.setSupportTime(supportTime);
        return reportRepository.save(report);   // Update the report in the database
    }

    public Report findReportById(UUID uid) {
        Optional<Report> reportOptional = reportRepository.findById(uid);
        return reportOptional.orElse(null);
    }

    public List<ReportSummary> getAllReports() {
        return reportRepository.findAll().stream()
                .map(report -> new ReportSummary(report.getUid(), report.getPartnerName(), report.getSupportTime(), report.getProject(),report.getSupportDate(), report.getNote()))
                .collect(Collectors.toList());
    }
    
}
