package com.rewards.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.type.LocalDateTimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rewards.controller.RewardsController;
import com.rewards.model.CustomerRegistrationBean;
import com.rewards.model.RewardPoints;
import com.rewards.model.TransactionBean;
import com.rewards.repository.RewardPointRepository;

@Service
public class RewardPointService {

	@Autowired
	private RewardPointRepository rewardPointRepository;
	
	  //private static Logger logger = LoggerFactory.getLogger(RewardPointService.class);


	// calculate reward points
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

	// calculate and save reward points
	public boolean saveOrUpdateRewardPoints(int points, RewardPoints rewardPoints, Long cust_id,
			Map<String, Object> map) {
		rewardPoints.setPoints(points);
		rewardPoints.setAmount((BigDecimal) map.get("amount"));
		rewardPoints.setCust_id(cust_id);

		// LocalDateTime date = LocalDateTime.now();
		rewardPoints.setTransaction_date(new Date());

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
	
	//Get transaction month, transaction total amount and total reward points
	public TransactionBean getTransactionSummary(Long cust_id) {
		
        List<RewardPoints> transactions = rewardPointRepository.findByCustomerId(cust_id);
       
        TransactionBean result = new TransactionBean();
        
       if(transactions.isEmpty()) {
    	   return result;
       }else {
        List<Integer> months = transactions.stream()
                .map(RewardPoints::getMonth) // Extract month from each transaction
                .collect(Collectors.toList()); 
        
        BigDecimal totalAmount = rewardPointRepository.findTotalTransactionAmountByCustomerId(cust_id);
        Integer totalPoints = rewardPointRepository.findTotalRewardPointsByCustomerId(cust_id);
        
        List<TransactionBean> transactionDetails = new ArrayList<>();
        
        for (RewardPoints transaction : transactions) {
        	TransactionBean dto = new TransactionBean();
        	dto.setCust_id(transaction.getCust_id());
            dto.setAmount(transaction.getAmount());
            dto.setMonth(transaction.getMonth());
  
            transactionDetails.add(dto);
        }
        
        result.setCust_id(cust_id);
        result.setAmount(totalAmount);
        result.setPoints(totalPoints);
        result.setMonth(months.get(0));
        System.out.println(result);
		return result;
    }
   }
}