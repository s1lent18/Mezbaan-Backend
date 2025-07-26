package com.example.Mezbaan.controller;

import com.example.Mezbaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signUp")
    //@PreAuthorize("hasAuthority('USERS')")
    public ResponseEntity<?> addUser(@RequestBody UserService.UserSignInRequest request) {

        try {
            UserService.UserSignInResponse userSignInResponse = userService.addUser(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(userSignInResponse);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
