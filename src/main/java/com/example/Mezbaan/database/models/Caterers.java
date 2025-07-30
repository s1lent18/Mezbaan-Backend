package com.example.Mezbaan.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "caterers")
public class Caterers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String address;

    @Column(name = "locationlink")
    private String locationLink;

    private Double rating;

    @Column(name = "coverimage")
    private String coverImage;

    @ManyToOne
    @JoinColumn(name = "vendorid", nullable = false)
    @JsonBackReference
    private Vendor vendor;

    public Caterers() {}

    public Caterers(String name, String address, String locationLink, Double rating, String coverImage) {
        this.name = name;
        this.address = address;
        this.locationLink = locationLink;
        this.rating = rating;
        this.coverImage = coverImage;
    }
}
