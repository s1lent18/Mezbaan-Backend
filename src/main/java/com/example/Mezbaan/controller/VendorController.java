package com.example.Mezbaan.controller;

import com.example.Mezbaan.JWT.JwtUtil;
import com.example.Mezbaan.database.models.Photographers;
import com.example.Mezbaan.database.models.Venues;
import com.example.Mezbaan.database.repository.PhotographersRepository;
import com.example.Mezbaan.database.repository.VenuesRepository;
import com.example.Mezbaan.service.PhotographersService;
import com.example.Mezbaan.service.VendorService;
import com.example.Mezbaan.service.VenuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    @Autowired
    VendorService vendorService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    VenuesService venuesService;

    @Autowired
    PhotographersService photographersService;

    @Autowired
    PhotographersRepository photographersRepository;

    @Autowired
    VenuesRepository venuesRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/signUp")
    public ResponseEntity<?> addUser(@RequestBody VendorService.VendorSignUpRequest request) {

        try {
            VendorService.VendorResponse vendorSignInResponse = vendorService.addVendor(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(vendorSignInResponse);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody VendorService.VendorSignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

            VendorService.VendorResponse getResponse = vendorService.getVendor(request);

            getResponse.token = jwtUtil.generateToken(userDetails);

            Map<String, VendorService.VendorResponse> response = new HashMap<>();
            response.put("vendorData", getResponse);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, VendorService.VendorResponse> response = new HashMap<>();

            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/addVenue")
    @PreAuthorize("hasAuthority('VENDORS')")
    public ResponseEntity<?> addVenue(@RequestBody VenuesService.AddVenue request) {
        try {
            String venue = venuesService.addVenue(request);

            Map<String, String> response = new HashMap<>();

            response.put("response", venue);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addPhotographer")
    @PreAuthorize("hasAuthority('VENDORS')")
    public ResponseEntity<?> addPhotographer(@RequestBody PhotographersService.AddPhotographers request) {
        try {
            String photographer = photographersService.addPhotographer(request);

            Map<String, String> response = new HashMap<>();

            response.put("response", photographer);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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