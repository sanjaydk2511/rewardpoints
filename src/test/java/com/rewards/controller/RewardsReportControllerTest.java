package com.rewards.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.rewards.model.RewardPoints;
import com.rewards.service.RewardPointService;

@SpringJUnitConfig
public class RewardsReportControllerTest {

    @InjectMocks
    private RewardsReportController controller;

    @Mock
    private RewardPointService rewardPointService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDownloadExcel_success() throws IOException {
        // Arrange
        List<RewardPoints> rewardsData = Arrays.asList(
            new RewardPoints(),
            new RewardPoints()
        );
        
        when(rewardPointService.getAllRewardPoints()).thenReturn((ArrayList<RewardPoints>) rewardsData);

        // Act
        ResponseEntity<byte[]> response = controller.downloadExcel();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        assertEquals("attachment; filename=reward_points_report.xlsx", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));

        // Validate the content of the file
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(response.getBody()))) {
            Sheet sheet = workbook.getSheetAt(0);
            assertNotNull(sheet);

            // Verify header
            Row headerRow = sheet.getRow(0);
            assertEquals("Customer Id", headerRow.getCell(0).getStringCellValue());
            assertEquals("Product Amount", headerRow.getCell(1).getStringCellValue());
            assertEquals("Reward Points", headerRow.getCell(2).getStringCellValue());
            assertEquals("Month", headerRow.getCell(3).getStringCellValue());
            assertEquals("Year", headerRow.getCell(4).getStringCellValue());

            // Verify data rows
            Row row1 = sheet.getRow(1);
            assertEquals(1L, row1.getCell(0).getNumericCellValue());
            assertEquals(100.00, row1.getCell(1).getNumericCellValue());
            assertEquals(10, row1.getCell(2).getNumericCellValue());
            assertEquals("January", row1.getCell(3).getStringCellValue());
            assertEquals(2024, row1.getCell(4).getNumericCellValue());

            Row row2 = sheet.getRow(2);
            assertEquals(2L, row2.getCell(0).getNumericCellValue());
            assertEquals(200.00, row2.getCell(1).getNumericCellValue());
            assertEquals(20, row2.getCell(2).getNumericCellValue());
            assertEquals("February", row2.getCell(3).getStringCellValue());
            assertEquals(2024, row2.getCell(4).getNumericCellValue());
        }
    }

    @Test
    public void testDownloadExcel_ioException() throws IOException {
        // Arrange
        when(rewardPointService.getAllRewardPoints()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        ReportGenerationException thrown = assertThrows(ReportGenerationException.class, () -> {
            controller.downloadExcel();
        });
        assertEquals("Failed to generate the report due to an internal error.", thrown.getMessage());
    }

    @Test
    public void testDownloadExcel_unexpectedException() throws IOException {
        // Arrange
        List<RewardPoints> rewardsData = Arrays.asList(
            new RewardPoints()
        );
        when(rewardPointService.getAllRewardPoints()).thenReturn((ArrayList<RewardPoints>) rewardsData);
        doThrow(new IOException("Write error")).when(rewardPointService).getAllRewardPoints();

        // Act & Assert
        ReportGenerationException thrown = assertThrows(ReportGenerationException.class, () -> {
            controller.downloadExcel();
        });
        assertEquals("An unexpected error occurred while generating the report.", thrown.getMessage());
    }
}