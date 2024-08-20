package com.rewards.controller;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.rewards.exception.ReportGenerationException;
import com.rewards.model.CustomerRegistrationBean;
import com.rewards.model.RewardPoints;
import com.rewards.service.CustomerDashboardService;
import com.rewards.service.RewardPointService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class RewardsReportController {

    @Autowired
    private RewardPointService rewardPointService;

    private static final Logger logger = LoggerFactory.getLogger(RewardsReportController.class);

    @GetMapping("/downloadRewardsReport")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
    	
    	try {
        List<RewardPoints> rewardsData = rewardPointService.getAllRewardPoints(); // Retrieve rewards points data from service

        // Create Excel workbook and sheet
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Rewards Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Customer Id", "Product Amount", "Reward Points", "Month", "Year"}; // Example column names
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Create data rows
            int rowNum = 1;
            for (RewardPoints data : rewardsData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getCust_id());

                BigDecimal bigDecimalValue = new BigDecimal(data.getAmount().doubleValue());
                double doubleValue = bigDecimalValue.doubleValue();
                row.createCell(1).setCellValue(doubleValue);

                row.createCell(2).setCellValue(data.getPoints());
                row.createCell(3).setCellValue(data.getMonth());
                row.createCell(4).setCellValue(data.getYear());
                // Add more fields as needed
            }

            // Write Excel to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "reward_points_report.xlsx");

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        }
    } catch (IOException e) {
        logger.error("Error generating report: {}", e.getMessage());
        throw new ReportGenerationException("Failed to generate the report due to an internal error.");
    } catch (Exception e) {
        logger.error("Unexpected error: {}", e.getMessage());
        throw new ReportGenerationException("An unexpected error occurred while generating the report.");
    }
}
}