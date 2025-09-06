package com.example.Mezbaan.database.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "photographerbooking")
public class PhotographerBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String time;

    private Integer price;

    private Integer advance;

    private Integer paid;

    @Column(name = "dateofadvance")
    private String dateOfAdvance;

    @Column(name = "dateofevent")
    private String dateOfEvent;

    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographerid", nullable = false)
    private Photographers photographer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid", nullable = false)
    private Users customer;

    public PhotographerBooking(String time, Integer price, Integer advance,
                        Integer paid, String dateOfAdvance, String dateOfEvent, Photographers photographer, Users customer) {
        this.time = time;
        this.price = price;
        this.advance = advance;
        this.paid = paid;
        this.dateOfAdvance = dateOfAdvance;
        this.dateOfEvent = dateOfEvent;
        this.photographer = photographer;
        this.customer = customer;
        this.status = "Pending for Approval";
    }
}
