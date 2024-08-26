package com.example.customer.app;

import com.example.customer.api.Customer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    @Test
    void toEntity_ShouldReturnEntity_WhenCustomerIsNotNull() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(12345L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAddress("1234 Elm Street");
        customer.setPhoneNumber("123-456-7890");
        customer.setDateOfBirth(LocalDate.of(1980, 1, 1));
        customer.setNationalSecurityNumber("123-45-6789");

        // Act
        CustomerEntity customerEntity = CustomerMapper.toEntity(customer);

        // Assert
        assertNotNull(customerEntity);
        assertEquals(12345L, customerEntity.getId());
        assertEquals("John", customerEntity.getFirstName());
        assertEquals("Doe", customerEntity.getLastName());
        assertEquals("1234 Elm Street", customerEntity.getAddress());
        assertEquals("123-456-7890", customerEntity.getPhoneNumber());
        assertEquals(LocalDate.of(1980, 1, 1), customerEntity.getDateOfBirth());
        assertEquals("123-45-6789", customerEntity.getNationalSecurityNumber());
    }

    @Test
    void toEntity_ShouldReturnNull_WhenCustomerIsNull() {
        // Arrange & Act
        CustomerEntity customerEntity = CustomerMapper.toEntity(null);

        // Assert
        assertNull(customerEntity);
    }

    @Test
    void toBean_ShouldReturnBean_WhenCustomerEntityIsNotNull() {
        // Arrange
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(12345L);
        customerEntity.setFirstName("Jane");
        customerEntity.setLastName("Doe");
        customerEntity.setAddress("5678 Oak Avenue");
        customerEntity.setPhoneNumber("987-654-3210");
        customerEntity.setDateOfBirth(LocalDate.of(1990, 2, 2));
        customerEntity.setNationalSecurityNumber("987-65-4321");

        // Act
        Customer customer = CustomerMapper.toBean(customerEntity);

        // Assert
        assertNotNull(customer);
        assertEquals(12345L, customer.getId());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("5678 Oak Avenue", customer.getAddress());
        assertEquals("987-654-3210", customer.getPhoneNumber());
        assertEquals(LocalDate.of(1990, 2, 2), customer.getDateOfBirth());
        assertEquals("987-65-4321", customer.getNationalSecurityNumber());
    }

    @Test
    void toBean_ShouldReturnNull_WhenCustomerEntityIsNull() {
        // Arrange & Act
        Customer customer = CustomerMapper.toBean(null);

        // Assert
        assertNull(customer);
    }
}