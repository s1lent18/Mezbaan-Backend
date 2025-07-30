package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Caterers;
import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.repository.CaterersRepository;
import com.example.Mezbaan.database.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaterersService {

    @Autowired
    CaterersRepository caterersRepository;

    @Autowired
    VendorRepository vendorRepository;

    public static class AddCaterer {
        public String name;
        public Vendor vendor;
        public String address;
        public String locationLink;
        public Double rating;
        public String coverImage;

        public AddCaterer(String name, Vendor vendor, String address, String locationLink, Double rating, String coverImage) {
            this.name = name;
            this.vendor = vendor;
            this.address = address;
            this.locationLink = locationLink;
            this.rating = rating;
            this.coverImage = coverImage;
        }
    }

    public String addCaterer(AddCaterer request) {
        vendorRepository.findById(request.vendor.getId()).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        Caterers caterer = new Caterers(
                request.name,
                request.address,
                request.locationLink,
                request.rating,
                request.coverImage
        );

        caterersRepository.save(caterer);

        return "Successfully Added Caterer";
    }

}