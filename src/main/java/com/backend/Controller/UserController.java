package com.backend.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.UserDTO;
import com.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long userId) {
        try {
            UserDTO userDTO = userService.getUserById(userId);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    //versioning in the api's
    // @GetMapping(value = "/{id}", params = "version")
    //we have to add the phone number field in dto class
    // public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long userId, @RequestParam("version") int version) {
    //     try {
    //         return switch (version) {
    //             case 1 -> ResponseEntity.ok(userService.getUserById(userId));
    //             case 2 -> ResponseEntity.ok(userService.retieveNameandPhoneNumber(userId));
    //             default -> ResponseEntity.status(400).body(null);
    //         }; // Bad Request for unsupported versions
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(404).body(null);
    //     }
    // }
    @GetMapping(value = "/{id}", params = "version")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable("id") Long userId, @RequestParam("version") int version) {
        
        try {
            return ResponseEntity.ok(userService.retieveNameandPhoneNumber(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
    // public Map<String, Object> retieveNameandPhoneNumber(Long userId) 
}