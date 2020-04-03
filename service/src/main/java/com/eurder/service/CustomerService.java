package com.eurder.service;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.infrastructure.authentication.FakeAuthenticationService;
import com.eurder.infrastructure.eurderRoles.EurderRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final FakeAuthenticationService fakeAuthenticationService;

    @Autowired
    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository, FakeAuthenticationService fakeAuthenticationService) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.fakeAuthenticationService = fakeAuthenticationService;
    }

    public Customer createCustomer(CustomerDto customerDto) {
        if (hasAnyEmptyFields(customerDto)) {
            throw new NotEverythingFilledInExeption("fill in everything");
        }
        if (lettersAreInputted(customerDto.getFirstname()) || lettersAreInputted(customerDto.getLastname())) {
            throw new NotEverythingFilledInExeption("nameinput not correct");
        }

        Customer customer = customerMapper.toCustomer(customerDto);
        customerRepository.addCustomer(customer);
        fakeAuthenticationService.addUser(customer.getFirstname(), "customer", List.of(EurderRole.CUSTOMER));
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return   customerRepository.getCustomerList();
    }

    public Customer getCustomer(int id) {
        return   customerRepository.getCustomerList().stream().filter(x -> x.getId() == id ).findFirst().orElse(null);
    }

    public boolean hasAnyEmptyFields(CustomerDto customerDto) {
        return customerDto.getAddress().isEmpty() || customerDto.getEmailadress().isEmpty() || customerDto.getFirstname().isEmpty() || customerDto.getLastname().isEmpty() || customerDto.getPhonenumber().isEmpty();
    }

    public boolean lettersAreInputted(String input) {
        return !input.chars().allMatch(Character::isLetter);
    }

    public FakeAuthenticationService getFakeAuthenticationService() {
        return fakeAuthenticationService;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }
}
