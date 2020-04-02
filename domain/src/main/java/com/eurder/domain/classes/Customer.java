package com.eurder.domain.classes;

import com.eurder.domain.mapper.CustomerFactory;

import java.util.Objects;

public class Customer {
    private int id;
    private String firstname;
    private String lastname;
    private String Emailadress;
    private String address;
    private String phonenumber;
    private static int counter =1;

    public Customer( CustomerFactory customerFactory) {
        this.id = counter++;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", Emailadress='" + Emailadress + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstname, customer.firstname) &&
                Objects.equals(lastname, customer.lastname) &&
                Objects.equals(Emailadress, customer.Emailadress) &&
                Objects.equals(address, customer.address) &&
                Objects.equals(phonenumber, customer.phonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, Emailadress, address, phonenumber);
    }
}

