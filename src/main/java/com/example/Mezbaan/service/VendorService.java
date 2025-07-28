package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.repository.VendorRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Getter @Setter
    public static class VendorSignUpRequest {
        public String email;
        public String password;
        public String image;
        public String name;
        public String type;

        public VendorSignUpRequest(String email, String password, String image, String name, String type) {
            this.email = email;
            this.password = password;
            this.image = image;
            this.name = name;
            this.type = type;
        }
    }

    @Getter @Setter
    public static class VendorSignInRequest {
        public String email;
        public String password;

        public VendorSignInRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter @Setter
    public static class VendorResponse {
        public String email;
        public String image;
        public String name;
        public String type;
        public String status;
        public String token;

        public VendorResponse(String email, String image, String name, String type, String status) {
            this.email = email;
            this.image = image;
            this.name = name;
            this.type = type;
            this.status = status;
        }
    }

    public VendorResponse addVendor(VendorSignUpRequest request) {

        if (vendorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Vendor already exists");
        }

        Vendor vendor = new Vendor(request.email, request.image, request.name, request.password, request.type);

        Vendor savedVendor = vendorRepository.save(vendor);

        savedVendor.setPassword(passwordEncoder.encode(savedVendor.getPassword()));

        vendorRepository.save(savedVendor);

        return new VendorResponse(
                savedVendor.getEmail(),
                savedVendor.getImage(),
                savedVendor.getName(),
                savedVendor.getType(),
                savedVendor.getStatus()
        );
    }
}