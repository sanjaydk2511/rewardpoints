package com.rewards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rewards.model.CustomerRegistrationBean;
import com.rewards.service.CustomerRegistrationService;

@Controller
public class CustomerRegistrationContorller {

	@Autowired
	CustomerRegistrationService service;
	
	private static Logger logger = LoggerFactory.getLogger(CustomerDashboardController.class);
	
	@RequestMapping("/register")
	public String customerRegistration(Model model) {
	
		return "customerRegistration";
	}
	
	@PostMapping("/custmerRegistrationSave")
	public String custmerRegistrationSave(CustomerRegistrationBean customerBean, RedirectAttributes redirectAttributes,Model model) {
		
		logger.debug("In custmerRegistrationSave cust id = " +customerBean.getCust_id());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	     
	     String passowrd = customerBean.getPassword();
	     String confirmPassowrd = customerBean.getConfirm_password();
	     
	     String encodedPassword = encoder.encode(passowrd);
	     String encodedConfirmPassword = encoder.encode(confirmPassowrd);
	     
	     logger.debug("Encoded Password: " + encodedPassword); //necessary for debugging purpose   
	     
	     customerBean.setPassword(encodedPassword);
	     customerBean.setConfirm_password(encodedConfirmPassword);
	     
		service.saveOrUpdateCustomer(customerBean);
		return "redirect:/customerdashboard";
	}
}