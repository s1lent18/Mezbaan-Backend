package com.example.Mezbaan.database.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "createdat")
    private Timestamp createdAt;

    private String email;

    private String image;

    private String name;

    private String password;

    public Users() {}

    @Enumerated(EnumType.STRING)
    private Status status;

    public Users(String email, String image, String name, String password) {
        this.email = email;
        this.image = image;
        this.name = name;
        this.password = password;
    }

    public Users(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}