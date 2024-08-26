package com.example.customer.api;

import com.example.customer.app.CustomerService;
import com.example.customer.app.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.example.customer.CustomerFixture.testCustomer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() throws Exception {
        // Arrange
        Customer customer1 = testCustomer("John");
        Customer customer2 = testCustomer("Jane");
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        // Act & Assert
        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void getCustomerById_ShouldReturnCustomer_WhenCustomerExists() throws Exception {
        // Arrange
        Long id = 1L;
        Customer customer = testCustomer("John");
        when(customerService.getCustomerById(id)).thenReturn(customer);

        // Act & Assert
        mockMvc.perform(get("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(customerService, times(1)).getCustomerById(id);
    }

    @Test
    void getCustomerById_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        // Arrange
        Long id = 1L;
        when(customerService.getCustomerById(id)).thenThrow(new CustomerNotFoundException());

        // Act & Assert
        mockMvc.perform(get("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).getCustomerById(id);
    }

    @Test
    void createCustomer_ShouldReturnCreatedCustomer() throws Exception {
        // Arrange
        Customer customer = testCustomer("John");
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        // Act & Assert
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"1234 Elm Street\",\"phoneNumber\":\"123-456-7890\",\"dateOfBirth\":\"1980-01-01\",\"nationalSecurityNumber\":\"123-45-6789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void updateCustomer_ShouldReturnUpdatedCustomer_WhenCustomerExists() throws Exception {
        // Arrange
        Long id = 1L;
        Customer customer = testCustomer("John");
        when(customerService.updateCustomer(eq(id), any(Customer.class))).thenReturn(customer);

        // Act & Assert
        mockMvc.perform(put("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"1234 Elm Street\",\"phoneNumber\":\"123-456-7890\",\"dateOfBirth\":\"1980-01-01\",\"nationalSecurityNumber\":\"123-45-6789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(customerService, times(1)).updateCustomer(eq(id), any(Customer.class));
    }

    @Test
    void updateCustomer_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        // Arrange
        Long id = 1L;
        when(customerService.updateCustomer(eq(id), any(Customer.class))).thenThrow(new CustomerNotFoundException());

        // Act & Assert
        mockMvc.perform(put("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"1234 Elm Street\",\"phoneNumber\":\"123-456-7890\",\"dateOfBirth\":\"1980-01-01\",\"nationalSecurityNumber\":\"123-45-6789\"}"))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).updateCustomer(eq(id), any(Customer.class));
    }

    @Test
    void deleteCustomer_ShouldReturnOk_WhenCustomerExists() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(customerService).deleteCustomer(id);

        // Act & Assert
        mockMvc.perform(delete("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomer(id);
    }

    @Test
    void deleteCustomer_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        // Arrange
        Long id = 1L;
        doThrow(new CustomerNotFoundException()).when(customerService).deleteCustomer(id);

        // Act & Assert
        mockMvc.perform(delete("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).deleteCustomer(id);
    }
}

