package com.example.Mezbaan.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "decorators")
public class Decorators {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "managername")
    private String managerName;

    @Column(name = "managernumber")
    private String managerNumber;

    private Double rating;

    @Column(name = "coverimage")
    private String coverImage;

    @ManyToOne
    @JoinColumn(name = "vendorid", nullable = false)
    @JsonBackReference
    private Vendor vendor;

    @OneToMany(mappedBy = "decorator", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Amenities> amenities;

    public Decorators(String name, String managerName, String managerNumber, Double rating, String coverImage) {
        this.name = name;
        this.managerName = managerName;
        this.managerNumber = managerNumber;
        this.rating = rating;
        this.coverImage = coverImage;
    }
}
