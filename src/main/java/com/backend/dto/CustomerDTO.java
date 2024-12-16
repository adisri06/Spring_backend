package com.backend.dto;

import com.backend.domain.Customer;

public class CustomerDTO {

    private Long phoneNumber;
    private String name;
    private Integer age;
    private Character gender;
    private String address;
    private String planId;


    public CustomerDTO(Long phoneNumber, String name, Integer age, Character gender, String address, String planId) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.planId = planId;
    }
    public CustomerDTO(){}

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
    @Override
    public String toString() {
        return "CustomerDTO{" +
                "phoneNumber=" + phoneNumber +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", planId='" + planId + '\'' +
                '}';
    }

    public static Customer prepareCustomerEntity(CustomerDTO customerDTO){
        Customer customerEntity = new Customer();
        customerEntity.setPhoneNumber(customerDTO.getPhoneNumber());
        customerEntity.setName(customerDTO.getName());
        customerEntity.setAge(customerDTO.getAge());
        customerEntity.setGender(customerDTO.getGender());
        customerEntity.setAddress(customerDTO.getAddress());
        customerEntity.setPlanId(customerDTO.getPlanId());
        return customerEntity;

    }
    

}
