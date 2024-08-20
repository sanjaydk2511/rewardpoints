package com.rewards.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.rewards.exception.ReportGenerationException;
import com.rewards.model.CustomerRegistrationBean;
import com.rewards.service.CustomerDashboardService;

@SpringJUnitConfig
public class CustomerReportContorllerTest {

    @InjectMocks
    private CustomerReportContorller controller;

    @Mock
    private CustomerDashboardService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDownloadExcel_success() throws IOException {
        // Arrange
        List<CustomerRegistrationBean> customerData = Arrays.asList(
            new CustomerRegistrationBean(),
            new CustomerRegistrationBean()
        );
        
        when(service.getDashboard()).thenReturn(customerData);

        // Act
        ResponseEntity<byte[]> response = controller.downloadExcel();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        assertEquals("attachment; filename=customer_report.xlsx", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));

        // Validate the content of the file
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(response.getBody()))) {
            Sheet sheet = workbook.getSheetAt(0);
            assertNotNull(sheet);

            // Verify header
            Row headerRow = sheet.getRow(0);
            assertEquals("Customer Id", headerRow.getCell(0).getStringCellValue());
            assertEquals("Customer Name", headerRow.getCell(1).getStringCellValue());
            assertEquals("Contact", headerRow.getCell(2).getStringCellValue());
            assertEquals("Email-id", headerRow.getCell(3).getStringCellValue());

            // Verify data rows
            Row row1 = sheet.getRow(1);
            assertEquals(1L, row1.getCell(0).getNumericCellValue());
            assertEquals("John Doe", row1.getCell(1).getStringCellValue());
            assertEquals("1234567890", row1.getCell(2).getStringCellValue());
            assertEquals("john@example.com", row1.getCell(3).getStringCellValue());

            Row row2 = sheet.getRow(2);
            assertEquals(2L, row2.getCell(0).getNumericCellValue());
            assertEquals("Jane Smith", row2.getCell(1).getStringCellValue());
            assertEquals("0987654321", row2.getCell(2).getStringCellValue());
            assertEquals("jane@example.com", row2.getCell(3).getStringCellValue());
        }
    }

    @Test
    public void testDownloadExcel_serviceException() throws IOException {
        // Arrange
        when(service.getDashboard()).thenThrow(new RuntimeException("Service error"));

        // Act
        RuntimeException thrown = assertThrows(ReportGenerationException.class, () -> {
            controller.downloadExcel();
        });

        // Assert
        assertEquals("Failed to generate the report due to an internal error.", thrown.getMessage());
    }
}