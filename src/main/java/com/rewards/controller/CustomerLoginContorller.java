package com.rewards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerLoginContorller {

	@RequestMapping("/")
	public String customerLogin(Model model) {
	
		return "login";
		
	}
	
	@RequestMapping("/login")
	public String customerLogin2(Model model) {
		
		return "login";
	}
	
}
