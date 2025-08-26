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
public class CatererBookingService {

    @Autowired
    CatererBookingRepository catererBookingRepository;

    @Autowired
    CaterersRepository caterersRepository;

    @Autowired
    UsersRepository usersRepository;

    public static class AddCatererBooking {
        public String time;
        public Integer guests;
        public Integer price;
        public Integer advance;
        public Integer paid;
        public String dateOfAdvance;
        public String dateOfEvent;
        public Integer catererId;
        public Integer customerId;

        public AddCatererBooking(String time, Integer guests, Integer price, Integer advance,
                               Integer paid, String dateOfAdvance, String dateOfEvent, Integer catererId, Integer customerId
        ) {
            this.time = time;
            this.guests = guests;
            this.price = price;
            this.advance = advance;
            this.paid = paid;
            this.dateOfAdvance = dateOfAdvance;
            this.dateOfEvent = dateOfEvent;
            this.catererId = catererId;
            this.customerId = customerId;
        }
    }

    @Transactional
    public String requestBooking(AddCatererBooking request) {

        Caterers caterers = caterersRepository.findById(request.catererId)
                .orElseThrow(() -> new RuntimeException("Venue Not Found"));

        usersRepository.findById(request.customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CatererBooking booking = new CatererBooking(
                caterers,
                request.time,
                request.guests,
                request.price,
                request.advance,
                request.paid,
                request.dateOfAdvance,
                request.dateOfEvent
        );

        catererBookingRepository.save(booking);

        return "Successfully Created the booking";
    }

    @Scheduled(cron = "59 59 23 * * ?")
    public void processDailyBookings() {

        List<CatererBooking> bookings = catererBookingRepository.findAll();

        List<Integer> ids = new ArrayList<>();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("h:mm a");

        for (CatererBooking booking : bookings) {
            String[] parts = booking.getTime().split("-");
            String endStr   = parts[1].trim();

            LocalTime endTime = LocalTime.parse(endStr, formatterTime);
            LocalDate eventDate = LocalDate.parse(booking.getDateOfEvent(), formatterDate);

            if (eventDate.isBefore(today) || (eventDate.isEqual(today) && now.isAfter(endTime))) {
                ids.add(booking.getId());
            }
        }

        if (!ids.isEmpty()) {
            catererBookingRepository.doneBooking(ids);
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
        catererBookingRepository.findById(request.id).orElseThrow(() -> new RuntimeException("Booking Not Found"));

        catererBookingRepository.processBooking(request.id);

        return request.msg;
    }

    public String cancelBooking(ConfirmBookingRequest request) {
        catererBookingRepository.findById(request.id).orElseThrow(() -> new RuntimeException("Booking Not Found"));

        catererBookingRepository.cancelBooking(request.id);

        return request.msg;
    }
}