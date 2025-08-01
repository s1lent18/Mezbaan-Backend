package com.example.Mezbaan.controller;

import com.example.Mezbaan.JWT.JwtUtil;
import com.example.Mezbaan.database.models.Caterers;
import com.example.Mezbaan.database.models.Photographers;
import com.example.Mezbaan.database.models.Users;
import com.example.Mezbaan.database.models.Venues;
import com.example.Mezbaan.database.repository.CaterersRepository;
import com.example.Mezbaan.database.repository.PhotographersRepository;
import com.example.Mezbaan.database.repository.VenuesRepository;
import com.example.Mezbaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    CaterersRepository caterersRepository;

    @Autowired
    PhotographersRepository photographersRepository;

    @Autowired
    VenuesRepository venuesRepository;

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

    @GetMapping("/getCaterer")
    public ResponseEntity<Map<String, Object>> getCaterer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Caterers> caterersPage = caterersRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("caterer", caterersPage.getContent());
        response.put("currentPage", caterersPage.getNumber());
        response.put("totalItems", caterersPage.getTotalElements());
        response.put("totalPages", caterersPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getPhotographer")
    public ResponseEntity<Map<String, Object>> getPhotographers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Photographers> photographersPage = photographersRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("photographer", photographersPage.getContent());
        response.put("currentPage", photographersPage.getNumber());
        response.put("totalItems", photographersPage.getTotalElements());
        response.put("totalPages", photographersPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getVenues")
    public ResponseEntity<Map<String, Object>> getVenues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Venues> venuesPage = venuesRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("venues", venuesPage.getContent());
        response.put("currentPage", venuesPage.getNumber());
        response.put("totalItems", venuesPage.getTotalElements());
        response.put("totalPages", venuesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}