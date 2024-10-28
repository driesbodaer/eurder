package com.eurder.service;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDtoRABBIT;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.infrastructure.authentication.FakeAuthenticationService;
import com.eurder.infrastructure.eurderRoles.EurderRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public CustomerDtoRABBIT createCustomer(CustomerDtoRABBIT customerDtoRABBIT) {
        if (hasAnyEmptyFields(customerDtoRABBIT)) {
            throw new NotEverythingFilledInExeption("fill in everything");
        }
        if (lettersAreInputted(customerDtoRABBIT.getFirstname()) || lettersAreInputted(customerDtoRABBIT.getLastname())) {
            throw new NotEverythingFilledInExeption("nameinput not correct");
        }

        Customer customer = customerMapper.toCustomer(customerDtoRABBIT);
        customerRepository.addCustomer(customer);
        fakeAuthenticationService.addUser(customer.getFirstname(), "customer", List.of(EurderRole.CUSTOMER));
        return customerDtoRABBIT;
    }

    public boolean hasAnyEmptyFields(CustomerDtoRABBIT customerDtoRABBIT) {
        return customerDtoRABBIT.getAddress().isEmpty() || customerDtoRABBIT.getEmailadress().isEmpty() || customerDtoRABBIT.getFirstname().isEmpty() || customerDtoRABBIT.getLastname().isEmpty() || customerDtoRABBIT.getPhonenumber().isEmpty();
    }

    public boolean lettersAreInputted(String input) {
        return !input.chars().allMatch(Character::isLetter);
    }

    public List<CustomerDtoRABBIT> getAllCustomers() {
       return customerRepository.getCustomerList().stream().map(x -> customerMapper.toCustomerDto(x)).collect(Collectors.toList());

    }

    public CustomerDtoRABBIT getCustomer(int id) {
        return customerMapper.toCustomerDto(Objects.requireNonNull(customerRepository.getCustomerList().stream().filter(x -> x.getId() == id).findFirst().orElse(null)));
    }

    public FakeAuthenticationService getFakeAuthenticationService() {
        return fakeAuthenticationService;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }
}
