package com.backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.backend.domain.User;
import com.backend.dto.UserDTO;
import com.backend.repository.UserRepository;


@Service
public class UserService implements UserServiceInterface  {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDTO getUserById(Long userId)  {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            // Convert User entity to UserDTO
            User user = userOptional.get();
            return user.convertToUserDTO(user);
        } else {
            throw new RuntimeException("User with id " + userId + " not found");
        }
    
    }
    @Override
    public Map<String, Object> retieveNameandPhoneNumber(Long userId)  {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            
            // Convert User entity to UserDTO
            User user = userOptional.get();
            UserDTO userDTO = user.convertToUserDTO(user);
            Long phoneNumber = getapicall(userDTO);
            Map<String, Object>  result = new HashMap<>();
        result.put("name", userDTO.getName());
        result.put("phoneNumber", phoneNumber);
        return result;

        } else {
            throw new RuntimeException("User with id " + userId + " not found");
        }
    
    }

    public static Long getapicall(UserDTO userDTO) { 
        System.out.println("Calling API"+ userDTO.getName());
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/customer/customerName/" + userDTO.getName();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{} ";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            // Make the API call
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> responseBody = response.getBody();

            // Extract phone number from the response
            if (responseBody != null && responseBody.containsKey("phoneNumber")) {
               Long phoneNumber = (Long) responseBody.get("phoneNumber");
                System.out.println("Phone number: " + phoneNumber);
                return phoneNumber;
            } else {
                throw new RuntimeException("Phone number not found in API response.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching phone number: " + e.getMessage(), e);
        }
    }
}
