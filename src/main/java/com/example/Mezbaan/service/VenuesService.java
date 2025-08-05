package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Amenities;
import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.models.Venues;
import com.example.Mezbaan.database.repository.AmenitiesRepository;
import com.example.Mezbaan.database.repository.VendorRepository;
import com.example.Mezbaan.database.repository.VenuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VenuesService {

    @Autowired
    VenuesRepository venuesRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    AmenitiesRepository amenitiesRepository;

    public static class AddVenue {
        public Integer vendorId;
        public String name;
        public String description;
        public String address;
        public Integer baseGuestCount;
        public Integer capacity;
        public String venueType;
        public Integer priceDay;
        public Integer priceNight;
        public Integer incStep;
        public Integer incPrice;
        public String locationLink;
        public String managerName;
        public String managerNumber;
        public List<Amenities> amenities;

        public AddVenue(Integer vendorId, String name, String description, String address,
                               Integer baseGuestCount, Integer capacity, String venueType, Integer priceDay, Integer priceNight,
                               Integer incStep, Integer incPrice, String locationLink, String managerName,
                               String managerNumber, List<Amenities> amenities
        ) {
            this.vendorId = vendorId;
            this.name = name;
            this.description = description;
            this.address = address;
            this.baseGuestCount = baseGuestCount;
            this.capacity = capacity;
            this.venueType = venueType;
            this.priceDay = priceDay;
            this.priceNight = priceNight;
            this.incStep = incStep;
            this.incPrice = incPrice;
            this.locationLink = locationLink;
            this.managerName = managerName;
            this.managerNumber = managerNumber;
            this.amenities = amenities;
        }
    }

    @Transactional
    public String addVenue(AddVenue request) {

        Vendor vendor = vendorRepository.findById(request.vendorId).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        List<Amenities> saveAmenities = new ArrayList<>();

        Venues venue = new Venues(
            vendor,
            request.name,
            request.description,
            request.address,
            request.baseGuestCount,
            request.capacity,
            request.venueType,
            request.priceDay,
            request.priceNight,
            request.incStep,
            request.incPrice,
            request.locationLink,
            request.managerName,
            request.managerNumber
        );

        Venues savedVenue = venuesRepository.save(venue);

        for (Amenities amenity : request.amenities) {
            Amenities it = new Amenities(
                    amenity.getName(),
                    amenity.getCost(),
                    savedVenue
            );
            saveAmenities.add(it);
        }

        amenitiesRepository.saveAll(saveAmenities);

        return "Successfully Added Venue";
    }

    @Transactional
    public String deleteVenue(Integer id, Integer vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        List<Venues> venues = vendor.getVenues();

        for (Venues venue : venues) {
            if (Objects.equals(venue.getId(), id)) {
                venuesRepository.delete(venue);
                return "Successfully Deleted Venue";
            }
        }

        return "No Venue Found";
    }
}