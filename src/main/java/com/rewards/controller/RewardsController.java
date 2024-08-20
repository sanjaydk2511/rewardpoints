package com.rewards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rewards.model.CustomerRegistrationBean;
import com.rewards.model.RewardPoints;
import com.rewards.service.CustomerDashboardService;
import com.rewards.service.RewardPointService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/calculatePoints/{cust_id}")
    public ResponseEntity<?> calculatePoints(@PathVariable Long cust_id) {
        logger.debug("Customer id : " + cust_id);
        CustomerRegistrationBean customer = service.getCustomerById(cust_id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/calculateRewardPoints")
    public ResponseEntity<?> calculatePoints(@RequestParam Long cust_id,
                                              @RequestParam BigDecimal amount,
                                              @RequestBody RewardPoints rewardPoints) {
        logger.debug("In calculatePoints method : cust_id: " + cust_id);

        if (cust_id != null) {
            int points = rewardPointService.calculatePoints(amount);
            Map<String, Object> response = new HashMap<>();
            response.put("cust_id", cust_id);
            response.put("points", points);
            response.put("amount", amount);

            logger.debug("response : " + response);

            rewardPointService.saveOrUpdateRewardPoints(rewardPoints, cust_id, response);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("Invalid customer ID");
    }

    @GetMapping("/allrewards")
    public ResponseEntity<?> getAllRewardPoints() {
    	ArrayList<RewardPoints> rewards = rewardPointService.getAllRewardPoints();
        if (rewards != null && !rewards.isEmpty()) {
            return ResponseEntity.ok(rewards);
        } else {
            logger.debug("From getAllRewardPoints : list is empty");
            return ResponseEntity.noContent().build();
        }
    }
}
