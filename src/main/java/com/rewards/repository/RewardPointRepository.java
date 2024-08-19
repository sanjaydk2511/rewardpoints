package com.rewards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rewards.model.RewardPoints;

@Repository
public interface RewardPointRepository extends JpaRepository<RewardPoints, Long> {

}