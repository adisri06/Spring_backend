package com.backend.dto;

import com.backend.domain.Address;

public class AddressDTO {
    private Long addressId;
    private String street;
    private String city;

    // Getters and Setters
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {  // Fixed getter naming
        return street;
    }

    public void setStreet(String street) {  // Fixed setter naming
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public static Address convertToAddress(AddressDTO addressDTO) {
        if (addressDTO == null) return null; // Null safety check

        Address address = new Address();
        address.setAddressId(addressDTO.getAddressId());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        return address;
    }
}