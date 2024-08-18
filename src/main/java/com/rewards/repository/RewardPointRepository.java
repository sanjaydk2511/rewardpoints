package com.rewards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rewards.model.RewardPoints;

@Repository
public interface RewardPointRepository extends JpaRepository<RewardPoints, Long> {

    // Find reward points by customer ID
	/*
	 * @Query("SELECT rp FROM RewardPoints rp WHERE rp.customer.id = :cust_id")
	 * List<RewardPoints> findByCustomerId(@Param("cust_id") Long cust_id);
	 * 
	 * // Find all reward points
	 * 
	 * @Query("SELECT rp FROM RewardPoint rp") List<RewardPoints>
	 * findAllRewardPoints();
	 */
}