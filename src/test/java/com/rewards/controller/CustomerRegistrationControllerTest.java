package com.rewards.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.rewards.exception.RegistrationException;
import com.rewards.model.CustomerRegistrationBean;
import com.rewards.service.CustomerRegistrationService;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
public class CustomerRegistrationControllerTest {

    @InjectMocks
    private CustomerRegistrationController controller;

    @Mock
    private CustomerRegistrationService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCustomerRegistration() {
        ResponseEntity<String> response = controller.customerRegistration();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer registration endpoint hit", response.getBody());
    }

    @Test
    public void testCustomerRegistrationSave_success() {
        CustomerRegistrationBean customerBean = new CustomerRegistrationBean();
        customerBean.setCust_id(1L);
        customerBean.setPassword("password123");
        customerBean.setConfirm_password("password123");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("password123");

        // Mock the service layer
        doNothing().when(service).saveOrUpdateCustomer(any(CustomerRegistrationBean.class));

        ResponseEntity<String> response = controller.customerRegistrationSave(customerBean);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer registered successfully", response.getBody());
        verify(service, times(1)).saveOrUpdateCustomer(any(CustomerRegistrationBean.class));
    }

    @Test
    public void testCustomerRegistrationSave_failure() {
        CustomerRegistrationBean customerBean = new CustomerRegistrationBean();
        customerBean.setCust_id(1L);
        customerBean.setPassword("password123");
        customerBean.setConfirm_password("password123");

        // Simulate an exception being thrown by the service
        doThrow(new RuntimeException("Database error")).when(service).saveOrUpdateCustomer(any(CustomerRegistrationBean.class));

        RuntimeException thrown = assertThrows(RegistrationException.class, () -> {
            controller.customerRegistrationSave(customerBean);
        });

        assertEquals("Registration failed due to an unexpected error", thrown.getMessage());
    }
}