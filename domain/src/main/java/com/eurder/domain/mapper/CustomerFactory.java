package com.eurder.domain.mapper;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;

public class CustomerFactory {
    private String firstname;
    private String lastname;
    private String Emailadress;
    private String address;
    private String phonenumber;

    public static CustomerFactory buildCustomer() {
        return new CustomerFactory();
    }

    public Customer build() {
        return new Customer( this);
    }
    public CustomerDto buildCustomerDto() {
        return new CustomerDto(this);
    }

    public CustomerFactory setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public CustomerFactory setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public CustomerFactory setEmailadress(String emailadress) {
        Emailadress = emailadress;
        return this;
    }

    public CustomerFactory setAddress(String address) {
        this.address = address;
        return this;
    }

    public CustomerFactory setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmailadress() {
        return Emailadress;
    }

    public String getAddress() {
        return address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}

