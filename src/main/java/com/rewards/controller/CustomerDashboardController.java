package com.rewards.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.exception.ResourceNotFoundException;
import com.rewards.model.CustomerRegistrationBean;
import com.rewards.service.CustomerDashboardService;

@RestController
@RequestMapping("/api/customers")
public class CustomerDashboardController {

    @Autowired
    private CustomerDashboardService service;

    private static final Logger logger = LoggerFactory.getLogger(CustomerDashboardController.class);

    @GetMapping
    public ResponseEntity<List<CustomerRegistrationBean>> viewCustomerDashboard() {
        logger.debug("In viewCustomerDashboard method:");

        List<CustomerRegistrationBean> customers = service.getDashboard();
        if (customers.isEmpty()) {
            logger.debug("From CustomerDashboardController: list is empty");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    
   
    @GetMapping("/{cust_id}")
    public ResponseEntity<CustomerRegistrationBean> getCustomerById(@PathVariable Long cust_id) {
    	logger.debug("In getCustomerById method: id = " + cust_id);
        Optional<CustomerRegistrationBean> employee = service.findCustomerById(cust_id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } 
        throw new ResourceNotFoundException("Customer with ID " + cust_id + " not found");
    }
    

    @PutMapping("/updateCustomer/{cust_id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long cust_id) {
        logger.debug("In updateCustomer method: id = " + cust_id);

        if (service.updateCustomer(cust_id)) {
            return new ResponseEntity<>("Update Success", HttpStatus.OK);
        }
       // return new ResponseEntity<>("Something went wrong..status not updated", HttpStatus.BAD_REQUEST);
        throw new ResourceNotFoundException("Customer with ID " + cust_id + " not found");
    }

    @GetMapping("/edit/{cust_id}")
    public ResponseEntity<CustomerRegistrationBean> editCustomer(@PathVariable Long cust_id) {
        logger.debug("In editCustomer Customer id : " + cust_id);
        CustomerRegistrationBean customer = service.getCustomerById(cust_id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        throw new ResourceNotFoundException("Customer with ID " + cust_id + " not found");
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editSaveCustomer(@RequestBody CustomerRegistrationBean customer) {
        logger.debug("In editSaveCustomer method : ");

        if (service.saveOrUpdateCustomer(customer)) {
            return new ResponseEntity<>("Edit Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Edit Failure", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{cust_id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long cust_id) {
        if (service.deleteCustomer(cust_id)) {
            return new ResponseEntity<>("Delete Success", HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Customer with ID " + cust_id + " not found");
    }
}
