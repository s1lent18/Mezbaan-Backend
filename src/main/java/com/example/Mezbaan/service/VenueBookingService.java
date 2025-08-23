package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Users;
import com.example.Mezbaan.database.models.VenueBooking;
import com.example.Mezbaan.database.models.Venues;
import com.example.Mezbaan.database.repository.UsersRepository;
import com.example.Mezbaan.database.repository.VenueBookingRepository;
import com.example.Mezbaan.database.repository.VenuesRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @Scheduled(cron = "59 59 23 * * ?")
    public void processDailyBookings() {

        List<VenueBooking> bookings = venueBookingRepository.findAll();

        List<Integer> ids = new ArrayList<>();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        LocalTime now = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("h:mm a");

        for (VenueBooking booking : bookings) {
            String[] parts = booking.getTime().split("-");
            String endStr   = parts[1].trim();

            LocalTime endTime = LocalTime.parse(endStr, formatterTime);
            LocalDate eventDate = LocalDate.parse(booking.getDateOfEvent(), formatterDate);

            if (eventDate.isBefore(today) || (eventDate.isEqual(today) && now.isAfter(endTime))) {
                ids.add(booking.getId());
            }
        }

        if (!ids.isEmpty()) {
            venueBookingRepository.doneBooking(ids);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ConfirmBookingRequest {
        public Integer id;
        public String msg;
    }

    public String confirmBooking(ConfirmBookingRequest request) {
        VenueBooking booking = venueBookingRepository.findById(request.id).orElseThrow(() -> new RuntimeException("Booking Not Found"));

        venueBookingRepository.processBooking(request.id);

        return request.msg;
    }
}
