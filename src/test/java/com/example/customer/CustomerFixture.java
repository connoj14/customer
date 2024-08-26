package com.example.customer;

import com.example.customer.api.Customer;
import com.example.customer.app.CustomerEntity;

import java.time.LocalDate;

public class CustomerFixture {

    public static Customer testCustomer(String firstName) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName("Doe");
        customer.setAddress("1234 Elm Street");
        customer.setPhoneNumber("080-322-3344");
        customer.setDateOfBirth(LocalDate.of(1980, 1, 1));
        customer.setNationalSecurityNumber("123-456-7890");
        return customer;
    }

    public static CustomerEntity testCustomerEntity(String firstName) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName("Doe");
        customerEntity.setAddress("1234 Elm Street");
        customerEntity.setPhoneNumber("080-322-3344");
        customerEntity.setDateOfBirth(LocalDate.of(1980, 1, 1));
        customerEntity.setNationalSecurityNumber("123-456-7890");
        return customerEntity;
    }
}
