package com.rewards.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.rewards.exception.RewardPointsException;
import com.rewards.model.CustomerRegistrationBean;
import com.rewards.model.RewardPoints;
import com.rewards.service.CustomerDashboardService;
import com.rewards.service.RewardPointService;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
public class RewardsControllerTest {

    @InjectMocks
    private RewardsController controller;

    @Mock
    private CustomerDashboardService service;

    @Mock
    private RewardPointService rewardPointService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculatePoints_customerFound() {
        // Arrange
        CustomerRegistrationBean customer = new CustomerRegistrationBean();
        when(service.getCustomerById(anyLong())).thenReturn(customer);

        // Act
        ResponseEntity<?> response = controller.calculatePoints(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testCalculatePoints_customerNotFound() {
        // Arrange
        when(service.getCustomerById(anyLong())).thenReturn(null);

        // Act
        ResponseEntity<?> response = controller.calculatePoints(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCalculatePoints_errorFetchingCustomer() {
        // Arrange
        when(service.getCustomerById(anyLong())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RewardPointsException thrown = assertThrows(RewardPointsException.class, () -> {
            controller.calculatePoints(1L);
        });
        assertEquals("Failed to retrieve customer details.", thrown.getMessage());
    }

    @Test
    public void testCalculatePointsPoints_success() {
        // Arrange
        BigDecimal amount = new BigDecimal("100.00");
        RewardPoints rewardPoints = new RewardPoints();
        when(rewardPointService.calculatePoints(any(BigDecimal.class))).thenReturn(10);
        doNothing().when(rewardPointService).saveOrUpdateRewardPoints(any(RewardPoints.class), anyLong(), any(Map.class));

        // Act
        ResponseEntity<?> response = controller.calculatePoints(1L, amount, rewardPoints);

        // Assert
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("cust_id", 1L);
        expectedResponse.put("points", 10);
        expectedResponse.put("amount", amount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testCalculatePointsPoints_missingInput() {
        // Act & Assert
        RewardPointsException thrown = assertThrows(RewardPointsException.class, () -> {
            controller.calculatePoints(1L, null, new RewardPoints());
        });
        assertEquals("Invalid input: customer ID or amount is missing.", thrown.getMessage());
    }

    @Test
    public void testCalculatePointsPoints_errorCalculatingPoints() {
        // Arrange
        BigDecimal amount = new BigDecimal("100.00");
        RewardPoints rewardPoints = new RewardPoints();
        when(rewardPointService.calculatePoints(any(BigDecimal.class))).thenThrow(new RuntimeException("Calculation error"));

        // Act & Assert
        RewardPointsException thrown = assertThrows(RewardPointsException.class, () -> {
            controller.calculatePoints(1L, amount, rewardPoints);
        });
        assertEquals("Failed to calculate reward points.", thrown.getMessage());
    }

    @Test
    public void testGetAllRewardPoints_success() {
        // Arrange
        List<RewardPoints> rewards = new ArrayList<>();
        rewards.add(new RewardPoints());
        when(rewardPointService.getAllRewardPoints()).thenReturn((ArrayList<RewardPoints>) rewards);

        // Act
        ResponseEntity<?> response = controller.getAllRewardPoints();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rewards, response.getBody());
    }

    @Test
    public void testGetAllRewardPoints_noContent() {
        // Arrange
        when(rewardPointService.getAllRewardPoints()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> response = controller.getAllRewardPoints();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAllRewardPoints_errorFetchingRewards() {
        // Arrange
        when(rewardPointService.getAllRewardPoints()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RewardPointsException thrown = assertThrows(RewardPointsException.class, () -> {
            controller.getAllRewardPoints();
        });
        assertEquals("Failed to retrieve reward points.", thrown.getMessage());
    }
}