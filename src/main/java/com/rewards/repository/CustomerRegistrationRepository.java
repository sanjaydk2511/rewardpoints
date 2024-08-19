package com.rewards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rewards.model.CustomerRegistrationBean;


@Repository
public interface CustomerRegistrationRepository extends JpaRepository<CustomerRegistrationBean, Long> {
}
