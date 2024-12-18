package com.backend.service;

import java.util.Map;

import com.backend.dto.UserDTO;

public interface UserServiceInterface {
    public UserDTO getUserById(Long userId) ;
    public Map<String, Object> retieveNameandPhoneNumber(Long userId)  ;
}
