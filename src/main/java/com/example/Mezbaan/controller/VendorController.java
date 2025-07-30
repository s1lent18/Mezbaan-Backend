package com.example.Mezbaan.controller;

import com.example.Mezbaan.JWT.JwtUtil;
import com.example.Mezbaan.database.models.*;
import com.example.Mezbaan.database.repository.CaterersRepository;
import com.example.Mezbaan.database.repository.PhotographersRepository;
import com.example.Mezbaan.database.repository.VenuesRepository;
import com.example.Mezbaan.service.CaterersService;
import com.example.Mezbaan.service.PhotographersService;
import com.example.Mezbaan.service.VendorService;
import com.example.Mezbaan.service.VenuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
    CaterersService caterersService;

    @Autowired
    CaterersRepository caterersRepository;

    @Autowired
    VenuesRepository venuesRepository;

    @Autowired
    PhotographersRepository photographersRepository;

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

            return ResponseEntity.badRequest().body(response);
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

    @PostMapping("/addCaterer")
    @PreAuthorize("hasAuthority('VENDORS')")
    public ResponseEntity<?> addCaterer(@RequestBody CaterersService.AddCaterer request) {
        try {
            String caterer = caterersService.addCaterer(request);

            Map<String, String> response = new HashMap<>();

            response.put("response", caterer);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/vendor.addVendor")
    @SendTo("/vendor/public")
    public Vendor addVendor(@Payload Vendor vendor) {
        vendor.setChatStatus(Status.ONLINE);
        return vendor;
    }

    @MessageMapping("/vendor.disconnectVendor")
    @SendTo("/vendor/public")
    public Vendor disconnectVendor(@Payload Vendor vendor) {
        vendor.setChatStatus(Status.OFFLINE);
        return vendor;
    }

    @DeleteMapping("/caterer/{id}")
    public ResponseEntity<Map<String, String>> deleteCaterer(@PathVariable Integer id) {

        Map<String, String> response = new HashMap<>();

        try {
            if (!caterersRepository.existsById(id)) {

                response.put("message", "Caterer with ID " + id + " not found");

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(response);
            }

            response.put("message", "Caterer deleted successfully");

            caterersRepository.deleteById(id);
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("message", "Failed to delete caterer");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @DeleteMapping("/venues/{id}")
    public ResponseEntity<Map<String, String>> deleteVenues(@PathVariable Integer id) {

        Map<String, String> response = new HashMap<>();

        try {
            if (!venuesRepository.existsById(id)) {
                response.put("message", "Venue with ID " + id + " not found");

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(response);
            }

            response.put("message", "Venue deleted successfully");

            venuesRepository.deleteById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {

            response.put("message", "Failed to delete venue");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @DeleteMapping("/photographer/{id}")
    public ResponseEntity<Map<String, String>> deletePhotographer(@PathVariable Integer id) {

        Map<String, String> response = new HashMap<>();

        try {
            if (!photographersRepository.existsById(id)) {
                response.put("message", "Photographer with ID " + id + " not found");

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(response);
            }

            response.put("message", "Photographer deleted successfully");

            photographersRepository.deleteById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {

            response.put("message", "Failed to delete Photographer");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}