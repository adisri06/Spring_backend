package com.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
