package com.backend.dto;

import com.backend.domain.User;

public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private AddressDTO address;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }

    public static User convertToUser(UserDTO userDTO) {
        if (userDTO == null) return null; // Null safety check

        User user = new User();
        user.setUserid(userDTO.getUserId());  // Updated getter/setter names
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAddress(AddressDTO.convertToAddress(userDTO.getAddress()));
        return user;
    }
}