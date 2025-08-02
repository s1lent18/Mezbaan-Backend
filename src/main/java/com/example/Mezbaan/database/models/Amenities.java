package com.example.Mezbaan.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "amenities")
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venueid")
    @JsonBackReference("venue-amenities")
    private Venues venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decoratorid")
    @JsonBackReference("decorator-amenities")
    private Decorators decorator;

    public Amenities() {}

    public Amenities(String name, Integer cost, Venues venue) {
        this.name = name;
        this.cost = cost;
        this.venue = venue;
    }

    public Amenities(String name, Integer cost, Decorators decorator) {
        this.name = name;
        this.cost = cost;
        this.decorator = decorator;
    }
}