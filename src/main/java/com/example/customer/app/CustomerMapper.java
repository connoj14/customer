package com.example.customer.app;

import com.example.customer.api.Customer;

public class CustomerMapper {

    public static CustomerEntity toEntity(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId());
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setAddress(customer.getAddress());
        customerEntity.setPhoneNumber(customer.getPhoneNumber());
        customerEntity.setDateOfBirth(customer.getDateOfBirth());
        customerEntity.setNationalSecurityNumber(customer.getNationalSecurityNumber());
        return customerEntity;
    }

    public static Customer toBean(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(customerEntity.getId());
        customer.setFirstName(customerEntity.getFirstName());
        customer.setLastName(customerEntity.getLastName());
        customer.setAddress(customerEntity.getAddress());
        customer.setPhoneNumber(customerEntity.getPhoneNumber());
        customer.setDateOfBirth(customerEntity.getDateOfBirth());
        customer.setNationalSecurityNumber(customerEntity.getNationalSecurityNumber());
        return customer;
    }
}

