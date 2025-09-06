package com.example.Mezbaan.database.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "decoratorbooking")
public class DecoratorBooking {

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
    @JoinColumn(name = "decoratorid", nullable = false)
    private Decorators decorator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid", nullable = false)
    private Users customer;

    public DecoratorBooking(String time, Integer price, Integer advance,
                               Integer paid, String dateOfAdvance, String dateOfEvent, Decorators decorator, Users customer) {
        this.time = time;
        this.price = price;
        this.advance = advance;
        this.paid = paid;
        this.dateOfAdvance = dateOfAdvance;
        this.dateOfEvent = dateOfEvent;
        this.decorator = decorator;
        this.customer = customer;
        this.status = "Pending for Approval";
    }
}