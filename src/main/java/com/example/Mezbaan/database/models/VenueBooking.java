package com.example.Mezbaan.database.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "venuebooking")
@Getter @Setter
@NoArgsConstructor
public class VenueBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;

    private String time;

    private Integer guests;

    private Integer price;

    private Integer advance;

    private Integer paid;

    @Column(name = "dateofadvance")
    private String dateOfAdvance;

    @Column(name = "dateofevent")
    private String dateOfEvent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venueid", nullable = false)
    private Venues venue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid", nullable = false)
    private Users customer;

    public VenueBooking(String type, String time, Integer guests, Integer price, Integer advance,
                        Integer paid, String dateOfAdvance, String dateOfEvent, Venues venue, Users customer) {
        this.type = type;
        this.time = time;
        this.guests = guests;
        this.price = price;
        this.advance = advance;
        this.paid = paid;
        this.dateOfAdvance = dateOfAdvance;
        this.dateOfEvent = dateOfEvent;
        this.venue = venue;
        this.customer = customer;
    }
}
