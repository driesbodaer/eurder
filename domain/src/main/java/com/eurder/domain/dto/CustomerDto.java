package com.eurder.domain.dto;

import com.eurder.domain.mapper.CustomerFactory;

import java.util.Objects;

public class CustomerDto {
    private String firstname;
    private String lastname;
    private String emailadress;
    private String address;
    private String phonenumber;

    public CustomerDto(CustomerFactory customerFactory) {
        this.firstname = customerFactory.getFirstname();
        this.lastname = customerFactory.getLastname();
        emailadress = customerFactory.getEmailadress();
        this.address = customerFactory.getAddress();
        this.phonenumber = customerFactory.getPhonenumber();
    }

    public CustomerDto(String firstname, String lastname, String emailadress, String address, String phonenumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailadress = emailadress;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmailadress() {
        return emailadress;
    }

    public String getAddress() {
        return address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(emailadress, that.emailadress) &&
                Objects.equals(address, that.address) &&
                Objects.equals(phonenumber, that.phonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, emailadress, address, phonenumber);
    }
}

