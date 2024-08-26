package com.example.customer.app;

import com.example.customer.api.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.customer.app.CustomerMapper.toBean;
import static com.example.customer.app.CustomerMapper.toEntity;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll().stream().map(CustomerMapper::toBean).toList();
    }

    public Customer getCustomerById(Long id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return toBean(customer.get());
        } else {
            throw new CustomerNotFoundException();
        }
    }

    public Customer createCustomer(Customer customer) {
        return toBean(customerRepository.save(toEntity(customer)));
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Optional<CustomerEntity> customerOptional = customerRepository.findById(id);

        if (customerOptional.isPresent()) {
            return toBean(customerRepository.save(toEntity(customerDetails)));
        } else {
            throw new CustomerNotFoundException();
        }
    }

    public void deleteCustomer(Long id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new CustomerNotFoundException();
        }
    }
}

