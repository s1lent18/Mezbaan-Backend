package com.example.Mezbaan.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "venues")
public class Venues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private String address;

    @Column(name = "baseguestcount")
    private Integer baseGuestCount;

    private Integer capacity;

    @Column(name = "venuetype")
    private String venueType;

    @Column(name = "priceday")
    private Integer priceDay;

    @Column(name = "pricenight")
    private Integer priceNight;

    @Column(name = "incstep")
    private Integer incStep;

    @Column(name = "incprice")
    private Integer incPrice;

    @Column(name = "locationlink")
    private String locationLink;

    @Column(name = "managername")
    private String managerName;

    @Column(name = "managernumber")
    private String managerNumber;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "vendorid", nullable = false)
    @JsonBackReference
    private Vendor vendor;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    @JsonManagedReference("venue-amenities")
    private List<Amenities> amenities;

    public Venues() {};

    public Venues(Vendor vendor, String name, String description, String address, Integer baseGuestCount, Integer capacity, String venueType, Integer priceDay,
                  Integer priceNight, Integer incStep, Integer incPrice, String locationLink, String managerName, String managerNumber
    ) {
        this.vendor = vendor;
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
        this.rating = 0.0;
    }
}