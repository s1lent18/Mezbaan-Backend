package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Amenities;
import com.example.Mezbaan.database.models.Caterers;
import com.example.Mezbaan.database.models.Decorators;
import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.repository.AmenitiesRepository;
import com.example.Mezbaan.database.repository.DecoratorsRepository;
import com.example.Mezbaan.database.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Vendor vendor;
        String name;
        String managerName;
        String managerNumber;
        Double rating;
        String coverImage;
        List<Amenities> amenities;

        public AddDecorator(Vendor vendor, String name, String managerName, String managerNumber, Double rating,
                            String coverImage, List<Amenities> amenities
        ) {
            this.vendor = vendor;
            this.name = name;
            this.managerName = managerName;
            this.managerNumber = managerNumber;
            this.rating = rating;
            this.coverImage = coverImage;
            this.amenities = amenities;
        }
    }

    @Transactional
    public String addDecorator(AddDecorator request) {
        vendorRepository.findById(request.vendor.getId()).orElseThrow(() -> new RuntimeException("Vendor not Found"));

        Decorators decorator = new Decorators(
                request.name,
                request.managerName,
                request.managerNumber,
                request.rating,
                request.coverImage
        );

        decoratorsRepository.save(decorator);
        amenitiesRepository.saveAll(request.amenities);

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
