package com.rewards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.exception.RegistrationException;
import com.rewards.model.CustomerRegistrationBean;
import com.rewards.service.CustomerRegistrationService;


@RestController
@RequestMapping("/api")
public class CustomerRegistrationController {

    @Autowired
    private CustomerRegistrationService service;

    private static final Logger logger = LoggerFactory.getLogger(CustomerRegistrationController.class);

    @GetMapping("/register")
    public ResponseEntity<String> customerRegistration() {
        // Instead of returning a view, you can return a message or status
        return ResponseEntity.ok("Customer registration endpoint hit");
    }

    @PostMapping("/customerRegistrationSave")
    public ResponseEntity<String> customerRegistrationSave(@RequestBody CustomerRegistrationBean customerBean) {
        logger.debug("In customerRegistrationSave cust id = " + customerBean.getCust_id());
        try {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String password = customerBean.getPassword();
        String confirmPassword = customerBean.getConfirm_password();
        
        String encodedPassword = encoder.encode(password);
        String encodedConfirmPassword = encoder.encode(confirmPassword);
        
        logger.debug("Encoded Password: " + encodedPassword); // necessary for debugging purpose
        
        customerBean.setPassword(encodedPassword);
        customerBean.setConfirm_password(encodedConfirmPassword);
        
        service.saveOrUpdateCustomer(customerBean);
        
        return ResponseEntity.ok("Customer registered successfully");
        }catch (Exception e) {
            logger.error("Registration failed: {}", e.getMessage());
            throw new RegistrationException("Registration failed due to an unexpected error");
        }
    }
}