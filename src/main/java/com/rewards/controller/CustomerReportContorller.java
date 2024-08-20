package com.rewards.controller;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.rewards.model.CustomerRegistrationBean;
import com.rewards.service.CustomerDashboardService;
import com.rewards.service.CustomerRegistrationService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class CustomerReportContorller {

    @Autowired
    private CustomerDashboardService service;

    @GetMapping("/downloadCustomerReports")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
        List<CustomerRegistrationBean> customerData = service.getDashboard(); // Retrieve dashboard data from service

        // Create Excel workbook and sheet
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Customer Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Customer Id", "Customer Name", "Contact", "Email-id"}; // Example column names
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Create data rows
            int rowNum = 1;
            for (CustomerRegistrationBean data : customerData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getCust_id());
                row.createCell(1).setCellValue(data.getCust_name());
                row.createCell(2).setCellValue(data.getContact());
                row.createCell(3).setCellValue(data.getEmail());
                // Add more fields as needed
            }

            // Write Excel to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "customer_report.xlsx");

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        }
    }
}