package com.work.backend_report.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.backend_report.entity.Report;
import com.work.backend_report.repository.ReportRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(ReportGeneratorService.class);

    private final ReportRepository reportRepository;

    @Autowired
    public ReportGeneratorService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void generateExcel(HttpServletResponse response, LocalDate startingDate, LocalDate endingDate)
            throws Exception {

        List<Report> reports = reportRepository.findBySupportDateBetween(startingDate, endingDate);

        ServletOutputStream ops;
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFSheet sheet = workbook.createSheet("Reports Summary");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("uid");
            row.createCell(1).setCellValue("Tanggal Support");
            row.createCell(2).setCellValue("Nama Mitra");
            row.createCell(3).setCellValue("waktu support");
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            HSSFDataFormat dateFormat = workbook.createDataFormat();
            dateCellStyle.setDataFormat(dateFormat.getFormat("dd-MMMM-yyyy"));
            int dataRowIndex = 1;
            for (Report report : reports) {
                HSSFRow dataRow = sheet.createRow(dataRowIndex);
                dataRow.createCell(0).setCellValue(report.getUid());
                HSSFCell dateCell = dataRow.createCell(1);
                dateCell.setCellValue(report.getSupportDate());
                dateCell.setCellStyle(dateCellStyle);
                dataRow.createCell(2).setCellValue(report.getPartnerName());
                dataRow.createCell(3).setCellValue(report.getSupportTime());
                dataRowIndex++;
            }
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }
            ops = response.getOutputStream();
            workbook.write(ops);
            ops.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }

}
