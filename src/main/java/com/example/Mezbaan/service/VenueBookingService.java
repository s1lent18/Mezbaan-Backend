package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Users;
import com.example.Mezbaan.database.models.VenueBooking;
import com.example.Mezbaan.database.models.Venues;
import com.example.Mezbaan.database.repository.UsersRepository;
import com.example.Mezbaan.database.repository.VenueBookingRepository;
import com.example.Mezbaan.database.repository.VenuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VenueBookingService {

    @Autowired
    VenueBookingRepository venueBookingRepository;

    @Autowired
    VenuesRepository venuesRepository;

    @Autowired
    UsersRepository usersRepository;

    public static class AddVenueBooking {
        public String type;
        public String time;
        public Integer guests;
        public Integer price;
        public Integer advance;
        public Integer paid;
        public String dateOfAdvance;
        public String dateOfEvent;
        public Integer venueId;
        public Integer customerId;

        public AddVenueBooking(String type, String time, Integer guests, Integer price, Integer advance,
                               Integer paid, String dateOfAdvance, String dateOfEvent, Integer venueId, Integer customerId
        ) {
            this.type = type;
            this.time = time;
            this.guests = guests;
            this.price = price;
            this.advance = advance;
            this.paid = paid;
            this.dateOfAdvance = dateOfAdvance;
            this.dateOfEvent = dateOfEvent;
            this.venueId = venueId;
            this.customerId = customerId;
        }
    }

    @Transactional
    public String requestBooking(AddVenueBooking request) {
        Venues venue = venuesRepository.findById(request.venueId)
                .orElseThrow(() -> new RuntimeException("Venue Not Found"));

        Users customer = usersRepository.findById(request.customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        VenueBooking booking = new VenueBooking(
                request.type,
                request.time,
                request.guests,
                request.price,
                request.advance,
                request.paid,
                request.dateOfAdvance,
                request.dateOfEvent,
                venue,
                customer
        );

        venueBookingRepository.save(booking);

        return "Successfully Created the booking";
    }
}
