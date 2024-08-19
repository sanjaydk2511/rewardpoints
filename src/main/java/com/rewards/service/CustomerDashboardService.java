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
public class CustomerDashboardService {

	@Autowired
	CustomerRegistrationRepository repository;

	private static Logger logger = LoggerFactory.getLogger(CustomerDashboardService.class);

	public List<CustomerRegistrationBean> getDashboard() {

		ArrayList<CustomerRegistrationBean> customerList = new ArrayList<CustomerRegistrationBean>();
		repository.findAll().forEach(customers -> customerList.add(customers));
		System.out.println("Customer list :" + customerList.size());
		return customerList;
	}

	public CustomerRegistrationBean getCustomerById(Long cust_id) {
		 return repository.findById(cust_id).get();
	}
	public boolean updateCustomer(Long cust_id) {
		CustomerRegistrationBean customer = getCustomerById(cust_id);
		
		return saveOrUpdateCustomer(customer);
	}

	public boolean saveOrUpdateCustomer(CustomerRegistrationBean customer) {
		CustomerRegistrationBean updatedObj = repository.save(customer);
		if (getCustomerById(updatedObj.getCust_id()) != null) {
			logger.debug("Record saved/updated with Id= " + updatedObj.getCust_id());
			return true;
		}
		return false;
	}
	
	public boolean deleteCustomer(Long cust_id) {

		repository.deleteById(cust_id); // original code - repo.deleteById(id);
		logger.debug("Id " + cust_id + " deleted from the database");

		if (repository.findById(cust_id) == null) { // original code
			if (repository.findById(cust_id).empty() != null)
				return true;
		}

		return true;
	}
}
