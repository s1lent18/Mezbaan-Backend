package com.example.Mezbaan.database.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "createdat")
    private Timestamp createdAt;

    private String email;

    private String image;

    private String name;

    private String password;

    private String status;

    private String type;

    @OneToMany(mappedBy = "vendors", cascade = CascadeType.ALL)
    private List<Venues> venues;

    public Vendor() {};

    public Vendor(String email, String image, String name, String password, String type) {
        this.email = email;
        this.image = image;
        this.name = name;
        this.password = password;
        this.type = type;
        this.status = "Active";
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}