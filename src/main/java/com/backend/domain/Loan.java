package com.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Loan {

   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanId;
    private String loanType;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    private Address address;


    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", loanType='" + loanType + '\'' +
                ", amount=" + amount +
                ", address=" + address +
                ", user=" + user +
                '}';
    }


}
