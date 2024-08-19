package com.rewards.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rewards.model.RewardPoints;
import com.rewards.service.CustomerDashboardService;
import com.rewards.service.RewardPointService;

@Controller
public class RewardsController {

	private static Logger logger = LoggerFactory.getLogger(RewardsController.class);

	@Autowired
	CustomerDashboardService service;

	@RequestMapping("/rewards")
	public String bcSystem(Model model) {
		return "RewardsHome";
		
	}

	@Autowired
	private final RewardPointService rewardPointService;

	public RewardsController(RewardPointService rewardPointService) {
		this.rewardPointService = rewardPointService;
	}

	@GetMapping("/calculatePoints/{cust_id}")
	public String calculatePoints(@PathVariable Long cust_id, Model model) {
		logger.debug("Customer id : " + cust_id);
		model.addAttribute("customer", service.getCustomerById(cust_id));
		return "rewardPoints";

	}

	@PostMapping("/calculateRewardPoints")
	public String calculatePoints(RewardPoints rewardPoints, @RequestParam Long cust_id,
			@RequestParam BigDecimal amount) {

		logger.debug("In calculatePoints method : cust_id: " + cust_id);

		if (cust_id != null) {
			int points = rewardPointService.calculatePoints(amount);
			Map<String, Object> map = new HashMap<>();

			map.put("cust_id", cust_id);
			map.put("points", points);
			map.put("amount", amount);

			logger.debug("map : " + map);

			rewardPointService.saveOrUpdateRewardPoints(rewardPoints, cust_id, map);
			return "redirect:/customerdashboard";
		}
		return "redirect:/customerdashboard";
	}


	@GetMapping("/allrewards")
	public String getAllRewardPoints(Model model, @ModelAttribute("message") String message) {

		if (!rewardPointService.getAllRewardPoints().isEmpty()) {
			model.addAttribute("rewards", rewardPointService.getAllRewardPoints());
		} else {
			logger.debug("From getAllRewardPoints : list is empty");
		}
		return "rewardReports";
	}

}
