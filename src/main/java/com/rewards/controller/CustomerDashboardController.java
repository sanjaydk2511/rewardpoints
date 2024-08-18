package com.rewards.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.rewards.model.CustomerRegistrationBean;
import com.rewards.service.CustomerDashboardService;
import com.rewards.service.CustomerRegistrationService;

@Controller
public class CustomerDashboardController {

	@Autowired
	private CustomerDashboardService service;

	private static Logger logger = LoggerFactory.getLogger(CustomerDashboardController.class);

	@GetMapping("/customerdashboard")
	public String viewCustomerDashboard(Model model, @ModelAttribute("message") String message) {
		logger.debug("In viewCustomerDashboard method:");
		
		if (!service.getDashboard().isEmpty()) {
			model.addAttribute("customers", service.getDashboard());

		} else {
			logger.debug("From CustomerDashboardController : list is empty");
		}
		return "customerDashboard";
	}

	@GetMapping("/updateCustomer/{cust_id}")
	public String updateCustomer(@PathVariable Long cust_id, RedirectAttributes redirectAttributes) {
		logger.debug("In updateCustomer method : id = " + cust_id);

		if (service.updateCustomer(cust_id)) {
			redirectAttributes.addFlashAttribute("message", "Update Success");
			return "redirect:/customerdashboard";
		}
		redirectAttributes.addFlashAttribute("message", "Something went wrong..status not updated");
		return "redirect:/customerdashboard";
	}

	@GetMapping("/editCustomer/{cust_id}")
	public String editCustomer(@PathVariable Long cust_id, Model model) {
		logger.debug("In editCustomer Customer id : " + cust_id);
		model.addAttribute("customer", service.getCustomerById(cust_id));
		return "editCustomers";

	}
	
	
	@PostMapping("/editSaveCustomer")
	public String editSaveCustomer(CustomerRegistrationBean customer, RedirectAttributes redirectAttributes, Model model) {

		logger.debug("In editSaveCustomer method : ");

		if (service.saveOrUpdateCustomer(customer)) {
			redirectAttributes.addFlashAttribute("message", "Edit Success");
			return "redirect:/customerdashboard";
		}
		redirectAttributes.addFlashAttribute("message", "Edit Failure");
		return "redirect:/customerdashboard/" + customer.getCust_id();
	}

	@GetMapping("/deleteCustomer/{cust_id}")
	public String deleteCustomer(@PathVariable Long cust_id, RedirectAttributes redirectAttributes) {
		if (service.deleteCustomer(cust_id)) {
			redirectAttributes.addFlashAttribute("message", "Delete Success");
			return "redirect:/customerdashboard";
		}

		redirectAttributes.addFlashAttribute("message", "Delete Failure");
		return "redirect:/customerdashboard";
	}

}
