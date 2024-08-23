package com.rewards.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.rewards.model.RewardPoints;
import com.rewards.model.TransactionBean;
import com.rewards.repository.RewardPointRepository;

@SpringBootTest
public class RewardPointServiceTest {

    @Mock
    private RewardPointRepository rewardPointRepository;

    @InjectMocks
    private RewardPointService rewardPointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCalculatePoints_Between50And100() {
        BigDecimal amount = BigDecimal.valueOf(75);
        int points = rewardPointService.calculatePoints(amount);
        assertEquals(25, points); // (75 - 50) = 25
    }

    @Test
    void testCalculatePoints_Below50() {
        BigDecimal amount = BigDecimal.valueOf(40);
        int points = rewardPointService.calculatePoints(amount);
        assertEquals(0, points); // No points
    }

    @Test
    void testSaveOrUpdateRewardPoints_Success() {
        Long cust_id = 1L;
        int points = 50;
        RewardPoints rewardPoints = new RewardPoints();
        Map<String, Object> map = new HashMap<>();
        map.put("amount", BigDecimal.valueOf(200));

        when(rewardPointRepository.save(any(RewardPoints.class))).thenReturn(rewardPoints);
        when(rewardPointRepository.findById(anyLong())).thenReturn(Optional.of(rewardPoints));

        boolean result = rewardPointService.saveOrUpdateRewardPoints(points, rewardPoints, cust_id, map);

        assertTrue(result);
        verify(rewardPointRepository, times(1)).save(rewardPoints);
    }

    @Test
    void testGetRewardItemById_Present() {
        Long cust_id = 3L;
        RewardPoints rewardPoints = new RewardPoints();
        when(rewardPointRepository.findById(cust_id)).thenReturn(Optional.of(rewardPoints));

        Optional<RewardPoints> result = rewardPointService.getRewardItemById(cust_id);

        assertTrue(result.isPresent());
        assertEquals(rewardPoints, result.get());
    }

    @Test
    void testGetRewardItemById_Absent() {
        Long cust_id = 4L;
        when(rewardPointRepository.findById(cust_id)).thenReturn(Optional.empty());

        Optional<RewardPoints> result = rewardPointService.getRewardItemById(cust_id);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllRewardPoints() {
        List<RewardPoints> rewardPointsList = Arrays.asList(new RewardPoints(), new RewardPoints());
        when(rewardPointRepository.findAll()).thenReturn(rewardPointsList);

        ArrayList<RewardPoints> result = rewardPointService.getAllRewardPoints();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rewardPointRepository, times(1)).findAll();
    }

}