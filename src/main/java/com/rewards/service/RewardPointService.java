package com.rewards.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.rewards.model.CustomerRegistrationBean;
import com.rewards.model.RewardPoints;
import com.rewards.repository.RewardPointRepository;

@Service
public class RewardPointService {

	
	  @Autowired 
	  private RewardPointRepository rewardPointRepository;
	 
	  //calculate reward points
    public int calculatePoints(BigDecimal amount) {
        int points = 0;
        if (amount.compareTo(BigDecimal.valueOf(100)) > 0) {
            BigDecimal above100 = amount.subtract(BigDecimal.valueOf(100));
            points += above100.intValue() * 2; // 2 points per dollar above $100
            amount = BigDecimal.valueOf(100);
        }
        if (amount.compareTo(BigDecimal.valueOf(50)) > 0) {
            BigDecimal between50And100 = amount.subtract(BigDecimal.valueOf(50));
            points += between50And100.intValue(); // 1 point per dollar between $50 and $100
        }
        return points;
    }
    
    //calculate and save reward points
    public boolean saveOrUpdateRewardPoints(int points, RewardPoints rewardPoints,Long cust_id, Map<String, Object> map) {
		rewardPoints.setPoints(points);
	    rewardPoints.setAmount((BigDecimal) map.get("amount"));
    	rewardPoints.setCust_id(cust_id);
    	
		RewardPoints bean = rewardPointRepository.save(rewardPoints);
		 
		if (getRewardItemById(rewardPoints.getId()) != null) {
			return true;
		}
		return false;
	}
    
    // Get reward points for a specific customer public 
	public Optional<RewardPoints> getRewardItemById(Long cust_id) {
		return rewardPointRepository.findById(cust_id); 
	}
   
	
	// Get all reward points
	public ArrayList<RewardPoints> getAllRewardPoints() {
		ArrayList<RewardPoints> rewardPoints = new ArrayList<RewardPoints>();
		rewardPointRepository.findAll().forEach(r -> rewardPoints.add(r));
		return rewardPoints;
	}
	
}