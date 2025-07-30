package com.example.Mezbaan.controller;

import com.example.Mezbaan.JWT.JwtUtil;
import com.example.Mezbaan.database.models.Users;
import com.example.Mezbaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/signUp")
    public ResponseEntity<?> addUser(@RequestBody UserService.UserSignUpRequest request) {

        try {
            UserService.UserSignInResponse userSignInResponse = userService.addUser(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(userSignInResponse);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserService.UserSignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

            UserService.UserSignInResponse getResponse = userService.getUser(request.email);

            getResponse.token = jwtUtil.generateToken(userDetails);

            Map<String, UserService.UserSignInResponse> response = new HashMap<>();
            response.put("userData", getResponse);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, UserService.UserSignInResponse> response = new HashMap<>();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public Users addUser(@Payload Users user) {
        userService.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public Users disconnectUser(@Payload Users user) {
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
}