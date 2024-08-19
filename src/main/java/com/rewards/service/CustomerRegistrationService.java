
package com.rewards.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewards.model.CustomerRegistrationBean;
import com.rewards.model.User;
import com.rewards.repository.CustomerRegistrationRepository;
import com.rewards.repository.UserRepository;


@Service
public class CustomerRegistrationService {

	@Autowired
	CustomerRegistrationRepository repository;

	@Autowired
	UserRepository userRepository;
	
	private static Logger logger = LoggerFactory.getLogger(CustomerRegistrationService.class);


	public CustomerRegistrationBean getCustomerItemById(Long cust_id) {
		return repository.findById(cust_id).get(); 
	}
	
	@Transactional
	public boolean saveOrUpdateCustomer(CustomerRegistrationBean register) {
	    logger.debug("In CustomerRegistrationService : Saving Customer information " + register.getCust_name());

	    // Save or update the customer registration
	    CustomerRegistrationBean bean = repository.save(register);

	    if (bean != null && getCustomerItemById(bean.getCust_id()) != null) {
	        logger.debug("Record saved/updated with Id= " + bean.getCust_id());

	        // Save or update passwords in two different tables
	        boolean passwordSaved1 = saveOrUpdatePasswordInTable1(bean);
	        boolean passwordSaved2 = saveOrUpdatePasswordInTable2(bean);

	        if (passwordSaved1 && passwordSaved2) {
	            return true;
	        } else {
	            // Handle case where passwords could not be saved
	            logger.error("Failed to save passwords in one or both tables.");
	            // Optionally, you might want to rollback the transaction here
	            return false;
	        }
	    }
	    return false;
	}

	private boolean saveOrUpdatePasswordInTable1(CustomerRegistrationBean bean) { // table customer_registration
	    // Create or update password entity for table 1
		
		CustomerRegistrationBean passwordEntity1 = new CustomerRegistrationBean();
	    passwordEntity1.setCust_id(bean.getCust_id());
	    passwordEntity1.setCust_name(bean.getCust_name());
	    passwordEntity1.setContact(bean.getContact());
	    passwordEntity1.setEmail(bean.getEmail());
	    passwordEntity1.setUser_name(bean.getUser_name());
	    passwordEntity1.setPassword(bean.getPassword()); // Assuming password is directly available
	    passwordEntity1.setConfirm_password(bean.getConfirm_password());
	    
	    // Save password entity to table 1
	    repository.save(passwordEntity1);
	    return true; // You may want to check if the save operation was successful
	}

	private boolean saveOrUpdatePasswordInTable2(CustomerRegistrationBean userBean) { //table users
	    // Create or update password entity for table 2
		User passwordEntity2 = new User();
		
	    passwordEntity2.setUsername(userBean.getUser_name());
	    passwordEntity2.setPassword(userBean.getPassword()); // Assuming password is directly available
	    passwordEntity2.setRoles("user");
	    passwordEntity2.setUsername(userBean.getUser_name());
	    
	    // Save password entity to table 2
	    userRepository.save(passwordEntity2);
	    return true; // You may want to check if the save operation was successful
	}
}
