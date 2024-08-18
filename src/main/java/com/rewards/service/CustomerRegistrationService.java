
package com.rewards.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewards.model.CustomerRegistrationBean;
import com.rewards.repository.CustomerRegistrationRepository;


@Service
public class CustomerRegistrationService {

	@Autowired
	CustomerRegistrationRepository repository;

	private static Logger logger = LoggerFactory.getLogger(CustomerRegistrationService.class);

	public boolean saveOrUpdateBCSystem(CustomerRegistrationBean register) {
		logger.debug("In CustomerRegistrationService : Saving Customer information " + register.getCust_name());
		CustomerRegistrationBean bean = repository.save(register);
		if (getCustomerItemById(bean.getCust_id()) != null) {
			logger.debug("Record saved/updated with Id= " + bean.getCust_id());
			return true;
		}
		return false;
	}

	public CustomerRegistrationBean getCustomerItemById(Long cust_id) {
		return repository.findById(cust_id).get(); 
	}

}
