package com.backend.dto;

public class UserLoanDTO {
    private Long userId;
    private String userName;
    private String userEmail;
    private Long addressId;
    private String street;
    private String city;
    private Long loanId;
    private String loanType;
    private double amount;

    public UserLoanDTO(Long userId, String userName, String userEmail, Long addressId, String street, String city,
                       Long loanId, String loanType, double amount) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.loanId = loanId;
        this.loanType = loanType;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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

    @Override
    public String toString() {
        return "USER_LOAN_DTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", addressId=" + addressId +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", loanId=" + loanId +
                ", loanType='" + loanType + '\'' +
                ", amount=" + amount +
                '}';
    }
}
