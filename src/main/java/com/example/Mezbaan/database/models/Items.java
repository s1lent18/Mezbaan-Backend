package com.example.Mezbaan.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "items")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer cost;

    private String type;

    @Column(name = "image")
    private String coverImage;

    @ManyToOne
    @JoinColumn(name = "catererid", nullable = false)
    @JsonBackReference
    private Caterers caterer;

    public Items() {}

    public Items(String name, Integer cost, String type, String coverImage, Caterers caterer) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.coverImage = coverImage;
        this.caterer = caterer;
    }
}
