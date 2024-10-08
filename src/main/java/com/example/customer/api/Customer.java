package com.example.customer.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class Customer {

    private Long id;
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;
    private String address;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{4}$", message = "National Security number must be in the format nnn-nn-nnnn")
    private String nationalSecurityNumber;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalSecurityNumber() {
        return nationalSecurityNumber;
    }
    public void setNationalSecurityNumber(String nationalSecurityNumber) {
        this.nationalSecurityNumber = nationalSecurityNumber;
    }
}

