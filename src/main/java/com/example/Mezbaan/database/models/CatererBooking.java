package com.example.Mezbaan.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "catererbooking")
public class CatererBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "catererid", nullable = false)
    @JsonBackReference
    private Caterers caterers;

    @OneToOne
    @JoinColumn(name = "bookingid", nullable = false)
    @JsonBackReference
    private ItemBooking itemBooking;

    private String time;

    private Integer guests;

    private Integer price;

    private Integer advance;

    private Integer paid;

    @Column(name = "dateofadvance")
    private String dateOfAdvance;

    @Column(name = "dateofevent")
    private String dateOfEvent;

    private String status;
}
