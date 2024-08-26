package com.example.customer.component;

import com.example.customer.api.Customer;
import com.example.customer.app.CustomerEntity;
import com.example.customer.app.CustomerRepository;
import com.example.customer.app.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.customer.CustomerFixture.testCustomer;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CustomerServiceComponentTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testCreateAndReadCustomer() {
        // Arrange
        Customer customer = testCustomer("John");

        // Act: Save the customer
        Customer createdCustomer = customerService.createCustomer(customer);

        // Assert: Verify that the customer is saved correctly
        assertNotNull(createdCustomer.getId());  // ID should be auto-generated and not null
        assertEquals(customer.getFirstName(), createdCustomer.getFirstName());

        // Act: Retrieve the customer by ID
        Optional<CustomerEntity> retrievedCustomerEntity = customerRepository.findById(createdCustomer.getId());
        assertTrue(retrievedCustomerEntity.isPresent());

        Customer retrievedCustomer = customerService.getCustomerById(createdCustomer.getId());

        // Assert: Verify that the retrieved customer matches the saved customer
        assertEquals(customer.getFirstName(), retrievedCustomer.getFirstName());
        assertEquals(customer.getLastName(), retrievedCustomer.getLastName());
        assertEquals(customer.getAddress(), retrievedCustomer.getAddress());
        assertEquals(customer.getPhoneNumber(), retrievedCustomer.getPhoneNumber());
        assertEquals(customer.getDateOfBirth(), retrievedCustomer.getDateOfBirth());
        assertEquals(customer.getNationalSecurityNumber(), retrievedCustomer.getNationalSecurityNumber());
    }

    @Test
    void testUpdateCustomer() {
        // Arrange: Create and save a customer
        Customer customer = testCustomer("John");

        Customer createdCustomer = customerService.createCustomer(customer);

        // Act: Update the customer
        createdCustomer.setLastName("Smith");
        Customer updatedCustomer = customerService.updateCustomer(createdCustomer.getId(), createdCustomer);

        // Assert: Verify that the customer was updated correctly
        assertEquals("Smith", updatedCustomer.getLastName());

        // Retrieve the customer and verify
        Customer retrievedCustomer = customerService.getCustomerById(updatedCustomer.getId());
        assertEquals("Smith", retrievedCustomer.getLastName());
    }

    @Test
    void testDeleteCustomer() {
        // Arrange: Create and save a customer
        Customer customer = testCustomer("John");

        Customer createdCustomer = customerService.createCustomer(customer);

        // Act: Delete the customer
        customerService.deleteCustomer(createdCustomer.getId());

        // Assert: Verify that the customer was deleted
        Optional<CustomerEntity> deletedCustomer = customerRepository.findById(createdCustomer.getId());
        assertFalse(deletedCustomer.isPresent());
    }
}
