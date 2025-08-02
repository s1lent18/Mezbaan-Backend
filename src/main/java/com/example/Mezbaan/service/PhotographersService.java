package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Photographers;
import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.repository.PhotographersRepository;
import com.example.Mezbaan.database.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PhotographersService {

    @Autowired
    PhotographersRepository photographersRepository;

    @Autowired
    VendorRepository vendorRepository;

    public static class AddPhotographers {
        Integer vendorId;
        String name;
        String description;
        String instaLink;
        String facebookLink;
        String email;
        String contactNumber;
        Integer cost;
        Integer teamSize;

        public AddPhotographers(
                Integer vendorId, String name, String description, String instaLink, String facebookLink, String email,
                String contactNumber, Integer cost, Integer teamSize
        ) {
            this.vendorId = vendorId;
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

    @Transactional
    public String addPhotographer(AddPhotographers request) {
        Vendor vendor = vendorRepository.findById(request.vendorId).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        Photographers photographer = new Photographers(
                request.name,
                request.description,
                request.instaLink,
                request.facebookLink,
                request.email,
                request.contactNumber,
                request.cost,
                request.teamSize,
                vendor
        );

        photographersRepository.save(photographer);

        return "Successfully Added Photographer";
    }

    @Transactional
    public String deletePhotographer(Integer id, Integer vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        List<Photographers> photographers = vendor.getPhotographers();

        for (Photographers photographer : photographers) {
            if (Objects.equals(photographer.getId(), id)) {
                photographersRepository.delete(photographer);
                return "Successfully Deleted Photographer";
            }
        }

        return "No Photographer Found";
    }
}
