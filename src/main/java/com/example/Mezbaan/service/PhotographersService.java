package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Photographers;
import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.repository.PhotographersRepository;
import com.example.Mezbaan.database.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotographersService {

    @Autowired
    PhotographersRepository photographersRepository;

    @Autowired
    VendorRepository vendorRepository;

    public static class AddPhotographers {
        Vendor vendor;
        String name;
        String description;
        String instaLink;
        String facebookLink;
        String email;
        String contactNumber;
        Integer cost;
        Integer teamSize;

        public AddPhotographers(
                Vendor vendor, String name, String description, String instaLink, String facebookLink, String email,
                String contactNumber, Integer cost, Integer teamSize
        ) {
            this.vendor = vendor;
            this.name = name;
            this.description = description;
            this.instaLink = instaLink;
            this.facebookLink = facebookLink;
            this.email = email;
            this.contactNumber = contactNumber;
            this.cost = cost;
            this.teamSize = teamSize;
        }
    }

    public String addPhotographer(AddPhotographers request) {
        vendorRepository.findById(request.vendor.getId()).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        Photographers photographer = new Photographers(
                request.name,
                request.description,
                request.instaLink,
                request.facebookLink,
                request.email,
                request.contactNumber,
                request.cost,
                request.teamSize
        );

        photographersRepository.save(photographer);

        return "Successfully Added Photographer";
    }
}
