package com.example.Mezbaan.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "bookingid", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemBooking> itemBookings;

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

    public CatererBooking(Caterers caterers, String time, Integer guests,
                          Integer price, Integer advance, Integer paid, String dateOfAdvance, String dateOfEvent)
    {
        this.caterers = caterers;
        this.time = time;
        this.guests = guests;
        this.price = price;
        this.advance = advance;
        this.paid = paid;
        this.dateOfAdvance = dateOfAdvance;
        this.dateOfEvent = dateOfEvent;
    }
}
