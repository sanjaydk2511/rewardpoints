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
	/*
	 * @Modifying
	 * 
	 * @Query("UPDATE customer_registration SET cust_name = :cust_name, contact = :contact, email = :email, WHERE cust_id = :cust_id"
	 * ) int updateCustomer(@Param("cust_id") Long cust_id,@Param("cust_name")
	 * String cust_name, @Param("contact") String contact,@Param("email") String
	 * email);
	 */
	//int updateCustomer(Long cust_id, String cust_name, String contact, String email);

	//void updateCustomer(CustomerRegistrationBean customer);

	//CustomerRegistrationBean updateCustomer(CustomerRegistrationBean customer);
}
