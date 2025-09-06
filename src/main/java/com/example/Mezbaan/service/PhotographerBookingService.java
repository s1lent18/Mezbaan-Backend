package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.*;
import com.example.Mezbaan.database.repository.*;
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
public class PhotographerBookingService {

    @Autowired
    PhotographerBookingRepository photographerBookingRepository;

    @Autowired
    PhotographersRepository photographersRepository;

    @Autowired
    UsersRepository usersRepository;

    public static class AddPhotographerBooking {
        public String time;
        public Integer price;
        public Integer advance;
        public Integer paid;
        public String dateOfAdvance;
        public String dateOfEvent;
        public Integer photographerId;
        public Integer customerId;

        public AddPhotographerBooking(String time, Integer price, Integer advance,
                                 Integer paid, String dateOfAdvance, String dateOfEvent, Integer photographerId, Integer customerId
        ) {
            this.time = time;
            this.price = price;
            this.advance = advance;
            this.paid = paid;
            this.dateOfAdvance = dateOfAdvance;
            this.dateOfEvent = dateOfEvent;
            this.photographerId = photographerId;
            this.customerId = customerId;
        }
    }

    @Transactional
    public String requestBooking(AddPhotographerBooking request) {

        Photographers photographers = photographersRepository.findById(request.photographerId)
                .orElseThrow(() -> new RuntimeException("Venue Not Found"));

        Users users = usersRepository.findById(request.customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PhotographerBooking booking = new PhotographerBooking(
                request.time,
                request.price,
                request.advance,
                request.paid,
                request.dateOfAdvance,
                request.dateOfEvent,
                photographers,
                users
        );

        photographerBookingRepository.save(booking);

        return "Successfully Created the booking";
    }

    @Scheduled(cron = "59 59 23 * * ?")
    public void processDailyBookings() {

        List<PhotographerBooking> bookings = photographerBookingRepository.findAll();

        List<Integer> ids = new ArrayList<>();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("h:mm a");

        for (PhotographerBooking booking : bookings) {
            String[] parts = booking.getTime().split("-");
            String endStr   = parts[1].trim();

            LocalTime endTime = LocalTime.parse(endStr, formatterTime);
            LocalDate eventDate = LocalDate.parse(booking.getDateOfEvent(), formatterDate);

            if (eventDate.isBefore(today) || (eventDate.isEqual(today) && now.isAfter(endTime))) {
                ids.add(booking.getId());
            }
        }

        if (!ids.isEmpty()) {
            photographerBookingRepository.doneBooking(ids);
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
        photographerBookingRepository.findById(request.id).orElseThrow(() -> new RuntimeException("Booking Not Found"));

        photographerBookingRepository.processBooking(request.id);

        return request.msg;
    }

    public String cancelBooking(ConfirmBookingRequest request) {
        photographerBookingRepository.findById(request.id).orElseThrow(() -> new RuntimeException("Booking Not Found"));

        photographerBookingRepository.cancelBooking(request.id);

        return request.msg;
    }
}