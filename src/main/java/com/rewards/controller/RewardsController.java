package com.rewards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rewards.exception.RewardPointsException;
import com.rewards.model.CustomerRegistrationBean;
import com.rewards.model.RewardPoints;
import com.rewards.service.CustomerDashboardService;
import com.rewards.service.RewardPointService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    private static Logger logger = LoggerFactory.getLogger(RewardsController.class);

    @Autowired
    private CustomerDashboardService service;

    @Autowired
    private final RewardPointService rewardPointService;

    public RewardsController(RewardPointService rewardPointService) {
        this.rewardPointService = rewardPointService;
    }



    @GetMapping("/calculateRewardPoints/{cust_id}/{amount}")
    public ResponseEntity<?> calculatePoints(@PathVariable Long cust_id, @PathVariable BigDecimal amount) {
        logger.debug("In calculatePoints method : cust_id: " + cust_id);

        try {
        	if (cust_id == null || amount == null) {
                throw new RewardPointsException("Invalid input: customer ID or amount is missing.");
            }
            int points = rewardPointService.calculatePoints(amount);
            return ResponseEntity.ok(points); 
        
        } catch (Exception e) {
            logger.error("Error calculating reward points: {}", e.getMessage());
            throw new RewardPointsException("Failed to calculate reward points.");
        }
        
    }
    
    @PostMapping("/saveRewardPoints")
    public ResponseEntity<?> savePoints(@RequestBody RewardPoints rewardPoints) {
        logger.debug("In calculatePoints method : cust_id: " );

        try {
       
        	if (rewardPoints.getCust_id() == null || rewardPoints.getAmount() == null) {
                throw new RewardPointsException("Invalid input: customer ID or amount is missing.");
            }
        	
            int points = rewardPointService.calculatePoints(rewardPoints.getAmount());
            logger.debug("Calculated Amount and points : " +rewardPoints.getAmount() +" "+ points);
            
            Map<String, Object> response = new HashMap<>();
            response.put("cust_id", rewardPoints.getCust_id());
            response.put("amount", rewardPoints.getAmount());

            logger.debug("response : " + response);

            rewardPointService.saveOrUpdateRewardPoints(points,rewardPoints, rewardPoints.getCust_id(), response);
            return ResponseEntity.ok(response);
        
        } catch (Exception e) {
            logger.error("Error calculating reward points: {}", e.getMessage());
            throw new RewardPointsException("Failed to calculate reward points.");
        }
        
    }
    
    @GetMapping("/getCustomerRewardPoints/{cust_id}")
    public ResponseEntity<?> getCustomerRewardPoints(@PathVariable Long cust_id) {
        logger.debug("Customer id : " + cust_id);
        try {
        	Optional<RewardPoints> rewards = rewardPointService.getRewardItemById(cust_id);
            if (rewards != null) {
                return ResponseEntity.ok(rewards);
            } else {
                logger.debug("From getCustomerRewardPoints");
                return ResponseEntity.noContent().build();
            }} catch (Exception e) {
                logger.error("Error fetching reward points: {}", e.getMessage());
                throw new RewardPointsException("Failed to retrieve reward points.");
            }
    }

    @GetMapping("/allrewards")
    public ResponseEntity<?> getAllRewardPoints() {
    	
    	  try {
    	ArrayList<RewardPoints> rewards = rewardPointService.getAllRewardPoints();
        if (rewards != null && !rewards.isEmpty()) {
            return ResponseEntity.ok(rewards);
        } else {
            logger.debug("From getAllRewardPoints : list is empty");
            return ResponseEntity.noContent().build();
        }} catch (Exception e) {
            logger.error("Error fetching reward points: {}", e.getMessage());
            throw new RewardPointsException("Failed to retrieve reward points.");
        }
    }
}
