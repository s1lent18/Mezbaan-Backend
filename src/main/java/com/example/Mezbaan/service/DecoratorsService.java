package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Amenities;
import com.example.Mezbaan.database.models.Decorators;
import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.repository.AmenitiesRepository;
import com.example.Mezbaan.database.repository.DecoratorsRepository;
import com.example.Mezbaan.database.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DecoratorsService {

    @Autowired
    DecoratorsRepository decoratorsRepository;

    @Autowired
    AmenitiesRepository amenitiesRepository;

    @Autowired
    VendorRepository vendorRepository;

    public static class AddDecorator {
        Integer vendorId;
        String name;
        String managerName;
        String managerNumber;
        String coverImage;
        List<Amenities> amenities;

        public AddDecorator(Integer vendorId, String name, String managerName, String managerNumber,
                            String coverImage, List<Amenities> amenities
        ) {
            this.vendorId = vendorId;
            this.name = name;
            this.managerName = managerName;
            this.managerNumber = managerNumber;
            this.coverImage = coverImage;
            this.amenities = amenities;
        }
    }

    @Transactional
    public String addDecorator(AddDecorator request) {
        Vendor vendor = vendorRepository.findById(request.vendorId).orElseThrow(() -> new RuntimeException("Vendor not Found"));

        List<Amenities> saveAmenities = new ArrayList<>();

        Decorators decorator = new Decorators(
                request.name,
                request.managerName,
                request.managerNumber,
                request.coverImage,
                vendor
        );

        Decorators savedDecorator = decoratorsRepository.save(decorator);

        for (Amenities amenity : request.amenities) {
            Amenities it = new Amenities(
                    amenity.getName(),
                    amenity.getCost(),
                    savedDecorator
            );
            saveAmenities.add(it);
        }

        amenitiesRepository.saveAll(saveAmenities);

        return "Successfully Added Decorator";
    }

    @Transactional
    public String deleteDecorator(Integer id, Integer vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        List<Decorators> decorators = vendor.getDecorators();

        for (Decorators decorator : decorators) {
            if (Objects.equals(decorator.getId(), id)) {
                decoratorsRepository.delete(decorator);
                return "Successfully Deleted Decorator";
            }
        }

        return "No Decorator Found";
    }
}