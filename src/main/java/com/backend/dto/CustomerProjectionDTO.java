package com.backend.dto;

public class CustomerProjectionDTO {
    private Long phoneNumber;
    private String name;
    private String planId;

    public CustomerProjectionDTO(Long phoneNumber, String name, String planId) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.planId = planId;
    }
    public CustomerProjectionDTO(){}

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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "CustomerProjectionDTO{" +
                "phoneNumber=" + phoneNumber +
                ", name='" + name + '\'' +
                ", planId=" + planId +
                '}';
    }


}
