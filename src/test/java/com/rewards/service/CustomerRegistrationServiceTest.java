package com.rewards.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.rewards.model.CustomerRegistrationBean;
import com.rewards.model.User;
import com.rewards.repository.CustomerRegistrationRepository;
import com.rewards.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class CustomerRegistrationServiceTest {

    @Mock
    private CustomerRegistrationRepository repository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerRegistrationService customerRegistrationService;

    private CustomerRegistrationBean customerBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a sample customer bean for testing
        customerBean = new CustomerRegistrationBean();
        customerBean.setCust_id(1L);
        customerBean.setCust_name("Rohit_Sharma");
        customerBean.setContact("9825456523");
        customerBean.setEmail("rohit.sharma@yahoo.com");
        customerBean.setUser_name("rohitman");
        customerBean.setPassword("roritika123");
    }

    @Test
    void testGetCustomerItemById() {
        when(repository.findById(1L)).thenReturn(Optional.of(customerBean));

        CustomerRegistrationBean result = customerRegistrationService.getCustomerItemById(1L);

        assertNotNull(result);
        assertEquals(customerBean.getCust_id(), result.getCust_id());
        assertEquals(customerBean.getCust_name(), result.getCust_name());
    }

   

    @Test
    void testSaveOrUpdatePasswordInTable1() {
        when(repository.save(any(CustomerRegistrationBean.class))).thenReturn(customerBean);

        boolean result = customerRegistrationService.saveOrUpdatePasswordInTable1(customerBean);

        assertTrue(result);
        verify(repository, times(1)).save(any(CustomerRegistrationBean.class));
    }

    @Test
    void testSaveOrUpdatePasswordInTable2() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean result = customerRegistrationService.saveOrUpdatePasswordInTable2(customerBean);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }
}