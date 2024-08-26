package com.example.customer.app;

import com.example.customer.api.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {
        // Arrange
        CustomerEntity entity1 = new CustomerEntity();
        CustomerEntity entity2 = new CustomerEntity();
        when(customerRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        // Act
        List<Customer> customers = customerService.getAllCustomers();

        // Assert
        assertNotNull(customers);
        assertEquals(2, customers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById_ShouldReturnCustomer_WhenCustomerExists() {
        // Arrange
        Long id = 1L;
        CustomerEntity entity = new CustomerEntity();
        when(customerRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        Customer customer = customerService.getCustomerById(id);

        // Assert
        assertNotNull(customer);
        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    void getCustomerById_ShouldThrowException_WhenCustomerDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(id));
        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    void createCustomer_ShouldReturnSavedCustomer() {
        // Arrange
        Customer customer = new Customer();
        CustomerEntity entity = new CustomerEntity();
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(entity);

        // Act
        Customer savedCustomer = customerService.createCustomer(customer);

        // Assert
        assertNotNull(savedCustomer);
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void updateCustomer_ShouldReturnUpdatedCustomer_WhenCustomerExists() {
        // Arrange
        Long id = 1L;
        Customer customerDetails = new Customer();
        CustomerEntity existingEntity = new CustomerEntity();
        when(customerRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingEntity);

        // Act
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);

        // Assert
        assertNotNull(updatedCustomer);
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void updateCustomer_ShouldThrowException_WhenCustomerDoesNotExist() {
        // Arrange
        Long id = 1L;
        Customer customerDetails = new Customer();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(id, customerDetails));
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void deleteCustomer_ShouldDeleteCustomer_WhenCustomerExists() {
        // Arrange
        Long id = 1L;
        CustomerEntity entity = new CustomerEntity();
        when(customerRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        customerService.deleteCustomer(id);

        // Assert
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteCustomer_ShouldThrowException_WhenCustomerDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(id));
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, never()).deleteById(id);
    }
}

