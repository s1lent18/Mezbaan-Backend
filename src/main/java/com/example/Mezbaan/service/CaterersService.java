package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Caterers;
import com.example.Mezbaan.database.models.Items;
import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.repository.CaterersRepository;
import com.example.Mezbaan.database.repository.ItemsRepository;
import com.example.Mezbaan.database.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CaterersService {

    @Autowired
    CaterersRepository caterersRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    ItemsRepository itemsRepository;

    public static class AddCaterer {
        public String name;
        public Integer vendorId;
        public String address;
        public String locationLink;
        public Double rating;
        public String coverImage;
        public List<Items> items;

        public AddCaterer(String name, Integer vendorId, String address,
                          String locationLink, Double rating, String coverImage, List<Items> items
        ) {
            this.name = name;
            this.vendorId = vendorId;
            this.address = address;
            this.locationLink = locationLink;
            this.rating = rating;
            this.coverImage = coverImage;
            this.items = items;
        }
    }

    @Transactional
    public String addCaterer(AddCaterer request) {
        Vendor vendor = vendorRepository.findById(request.vendorId).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        List<Items> itemsList = new ArrayList<>();

        Caterers caterer = new Caterers(
                vendor,
                request.name,
                request.address,
                request.locationLink,
                request.rating,
                request.coverImage
        );

        Caterers savedCaterer = caterersRepository.save(caterer);

        for (Items item : request.items) {
            Items it = new Items(
                    item.getName(),
                    item.getCost(),
                    item.getType(),
                    item.getCoverImage(),
                    savedCaterer
            );
            itemsList.add(it);
        }

        itemsRepository.saveAll(itemsList);

        return "Successfully Added Caterer";
    }

    @Transactional
    public String deleteCaterer(Integer id, Integer vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        List<Caterers> caterers = vendor.getCaterers();

        for (Caterers caterer : caterers) {
            if (Objects.equals(caterer.getId(), id)) {
                caterersRepository.delete(caterer);
                return "Successfully Deleted Caterer";
            }
        }

        return "No Caterer Found";
    }

}