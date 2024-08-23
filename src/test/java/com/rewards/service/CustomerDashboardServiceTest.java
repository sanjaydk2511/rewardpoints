package com.rewards.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.rewards.model.CustomerRegistrationBean;
import com.rewards.repository.CustomerRegistrationRepository;

import java.util.*;

@SpringBootTest
public class CustomerDashboardServiceTest {

    @Mock
    private CustomerRegistrationRepository repository;

    @InjectMocks
    private CustomerDashboardService customerDashboardService;

    private CustomerRegistrationBean customerBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a sample customer bean for testing
        customerBean = new CustomerRegistrationBean();
        customerBean.setCust_id(1L);
        customerBean.setCust_name("John_Cena");
        customerBean.setContact("9970836737");
        customerBean.setEmail("john.cena@yahoo.com");
        customerBean.setUser_name("johncena");
        customerBean.setPassword("jcena4334");
    }

    @Test
    void testGetDashboard() {
        List<CustomerRegistrationBean> customerList = Arrays.asList(customerBean, new CustomerRegistrationBean());
        when(repository.findAll()).thenReturn(customerList);

        List<CustomerRegistrationBean> result = customerDashboardService.getDashboard();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById() {
        when(repository.findById(1L)).thenReturn(Optional.of(customerBean));

        CustomerRegistrationBean result = customerDashboardService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals(customerBean.getCust_id(), result.getCust_id());
        assertEquals(customerBean.getCust_name(), result.getCust_name());
    }

    @Test
    void testFindCustomerById_Present() {
        when(repository.findById(1L)).thenReturn(Optional.of(customerBean));

        Optional<CustomerRegistrationBean> result = customerDashboardService.findCustomerById(1L);

        assertTrue(result.isPresent());
        assertEquals(customerBean, result.get());
    }

    @Test
    void testFindCustomerById_Absent() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<CustomerRegistrationBean> result = customerDashboardService.findCustomerById(1L);

        assertFalse(result.isPresent());
    }


    @Test
    void testSaveOrUpdateCustomer_Success() {
        when(repository.save(any(CustomerRegistrationBean.class))).thenReturn(customerBean);
        when(repository.findById(customerBean.getCust_id())).thenReturn(Optional.of(customerBean));

        boolean result = customerDashboardService.saveOrUpdateCustomer(customerBean);

        assertTrue(result);
        verify(repository, times(1)).save(customerBean);
        verify(repository, times(1)).findById(customerBean.getCust_id());
    }


    @Test
    void testDeleteCustomer_Success() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        customerDashboardService.deleteCustomer(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCustomer_Failure() {
        when(repository.findById(1L)).thenReturn(Optional.of(customerBean));

        customerDashboardService.deleteCustomer(1L);

        verify(repository, times(1)).deleteById(1L);
        // The check for existence after deletion might need modification if your actual implementation is different
        verify(repository, times(1)).findById(1L);
    }
}