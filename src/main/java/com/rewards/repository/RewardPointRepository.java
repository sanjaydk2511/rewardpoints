package com.rewards.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rewards.model.RewardPoints;

@Repository
public interface RewardPointRepository extends JpaRepository<RewardPoints, Long> {
	//List<RewardPoints> findByCustomerId(Long cust_id);
	
	@Query("SELECT rp FROM RewardPoints rp WHERE rp.cust_id = :cust_id")
    List<RewardPoints> findByCustomerId(@Param("cust_id") Long cust_id);
	
	@Query("SELECT SUM(rp.amount) FROM RewardPoints rp WHERE rp.cust_id = :cust_id")
    BigDecimal findTotalTransactionAmountByCustomerId(@Param("cust_id") Long cust_id);

	@Query("SELECT SUM(rp.points) FROM RewardPoints rp WHERE rp.cust_id = :cust_id")
    Integer findTotalRewardPointsByCustomerId(@Param("cust_id") Long cust_id);
}