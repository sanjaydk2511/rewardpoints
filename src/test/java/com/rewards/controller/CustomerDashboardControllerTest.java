package com.rewards.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.rewards.exception.ResourceNotFoundException;
import com.rewards.model.CustomerRegistrationBean;
import com.rewards.service.CustomerDashboardService;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
public class CustomerDashboardControllerTest {

    @InjectMocks
    private CustomerDashboardController controller;

    @Mock
    private CustomerDashboardService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewCustomerDashboard_withData() {
        List<CustomerRegistrationBean> customerList = new ArrayList<>();
        customerList.add(new CustomerRegistrationBean()); // assuming you have a default constructor

        when(service.getDashboard()).thenReturn(customerList);

        ResponseEntity<List<CustomerRegistrationBean>> response = controller.viewCustomerDashboard();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerList, response.getBody());
    }

    @Test
    public void testViewCustomerDashboard_noData() {
        when(service.getDashboard()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CustomerRegistrationBean>> response = controller.viewCustomerDashboard();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateCustomer_success() {
        Long customerId = 1L;

        when(service.updateCustomer(customerId)).thenReturn(true);

        ResponseEntity<String> response = controller.updateCustomer(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Update Success", response.getBody());
    }

    @Test
    public void testUpdateCustomer_failure() {
        Long customerId = 1L;

        when(service.updateCustomer(customerId)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            controller.updateCustomer(customerId);
        });

        assertEquals("Customer with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testEditCustomer_found() {
        Long customerId = 1L;
        CustomerRegistrationBean customer = new CustomerRegistrationBean();

        when(service.getCustomerById(customerId)).thenReturn(customer);

        ResponseEntity<CustomerRegistrationBean> response = controller.editCustomer(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testEditCustomer_notFound() {
        Long customerId = 1L;

        when(service.getCustomerById(customerId)).thenReturn(null);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            controller.editCustomer(customerId);
        });

        assertEquals("Customer with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testEditSaveCustomer_success() {
        CustomerRegistrationBean customer = new CustomerRegistrationBean();

        when(service.saveOrUpdateCustomer(customer)).thenReturn(true);

        ResponseEntity<String> response = controller.editSaveCustomer(customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Edit Success", response.getBody());
    }

    @Test
    public void testEditSaveCustomer_failure() {
        CustomerRegistrationBean customer = new CustomerRegistrationBean();

        when(service.saveOrUpdateCustomer(customer)).thenReturn(false);

        ResponseEntity<String> response = controller.editSaveCustomer(customer);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Edit Failure", response.getBody());
    }

    @Test
    public void testDeleteCustomer_success() {
        Long customerId = 1L;

        when(service.deleteCustomer(customerId)).thenReturn(true);

        ResponseEntity<String> response = controller.deleteCustomer(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Delete Success", response.getBody());
    }

    @Test
    public void testDeleteCustomer_failure() {
        Long customerId = 1L;

        when(service.deleteCustomer(customerId)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            controller.deleteCustomer(customerId);
        });

        assertEquals("Customer with ID 1 not found", exception.getMessage());
    }
}