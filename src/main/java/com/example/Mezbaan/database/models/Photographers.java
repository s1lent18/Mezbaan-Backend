package com.example.Mezbaan.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "photographers")
public class Photographers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(name = "instalink")
    private String instaLink;

    @Column(name = "facebooklink")
    private String facebookLink;

    private String email;

    @Column(name = "contactnumber")
    private String contactNumber;

    private Integer cost;

    @Column(name = "teamsize")
    private Integer teamSize;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "vendorid", nullable = false)
    @JsonBackReference
    private Vendor vendor;

    public Photographers(
            String name, String description, String instaLink, String facebookLink, String email,
            String contactNumber, Integer cost, Integer teamSize
    ) {
        this.name = name;
        this.description = description;
        this.instaLink = instaLink;
        this.facebookLink = facebookLink;
        this.email = email;
        this.contactNumber = contactNumber;
        this.cost = cost;
        this.teamSize = teamSize;
        this.rating = 0.0;
    }
}
