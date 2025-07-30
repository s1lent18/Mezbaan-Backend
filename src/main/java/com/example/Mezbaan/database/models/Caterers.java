package com.example.Mezbaan.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

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
}
