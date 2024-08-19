package com.rewards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerLoginContorller {

	@RequestMapping("/")
	public String customerLogin(Model model) {
		System.out.println("In default login : customedashboard");
		return "customerDashboard";
		
	}
	
}
