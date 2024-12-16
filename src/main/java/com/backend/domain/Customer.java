package com.backend.domain;

import com.backend.dto.CustomerDTO;
import com.backend.dto.CustomerProjectionDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    @Column(name = "phone_number")
    private Long phoneNumber;
    private String name;
    private Integer age;
    private Character gender;
    private String address;
    @Column(name= "plan_id")
    private String planId;

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

    public Customer(){}

    public Customer(Long phoneNumber, String name, Integer age, Character gender, String address, String planId) {
        //Super() is used to call the constructor of the parent class
        //its used to call the constructor of the parent class but not necessary here as we are not using any parent class
        super();
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.planId = planId;
    }

    //@Override is used to override the toString method of the parent class
    @Override
    public String toString() {
        return "Customer{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", planId='" + planId + '\'' +
                '}';
    }

    public static CustomerDTO prepareCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setName(customer.getName());        
        customerDTO.setAge(customer.getAge());
        customerDTO.setGender(customer.getGender());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setPlanId(customer.getPlanId());
        return customerDTO;
    }

    public static CustomerProjectionDTO prepareCustomerProjectionDTO(Customer customer){
        CustomerProjectionDTO customerDTO = new CustomerProjectionDTO();
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setName(customer.getName());
        return customerDTO;

    }
}
