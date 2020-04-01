package com.eurder.domain.classes;

import com.eurder.domain.mapper.CustomerFactory;

public class Customer {
    private String firstname;
    private String lastname;
    private String Emailadress;
    private String address;
    private String phonenumber;

    public Customer(CustomerFactory customerFactory) {
        this.firstname = customerFactory.getFirstname();
        this.lastname = customerFactory.getLastname();
        Emailadress = customerFactory.getEmailadress();
        this.address = customerFactory.getAddress();
        this.phonenumber = customerFactory.getPhonenumber();
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

