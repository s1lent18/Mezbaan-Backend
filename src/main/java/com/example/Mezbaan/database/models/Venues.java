package com.example.Mezbaan.database.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

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
    private Integer vendorId;

    public Venues(String name, String description, String address, Integer baseGuestCount, String venueType, Integer priceDay,
                  Integer priceNight, Integer incStep, Integer incPrice, String locationLink, String managerName, String managerNumber
    ) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.baseGuestCount = baseGuestCount;
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