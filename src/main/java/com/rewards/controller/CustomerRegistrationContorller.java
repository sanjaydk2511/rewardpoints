package com.rewards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
		
		logger.debug("In BCMembersController First name= " +customerBean.getCust_id());
		service.saveOrUpdateBCSystem(customerBean);
		return "redirect:/login";
		
	}
}
