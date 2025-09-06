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
public class DecoratorBookingService {

    @Autowired
    DecoratorBookingRepository decoratorBookingRepository;

    @Autowired
    DecoratorsRepository decoratorsRepository;

    @Autowired
    UsersRepository usersRepository;

    public static class AddDecoratorBooking {
        public String time;
        public Integer price;
        public Integer advance;
        public Integer paid;
        public String dateOfAdvance;
        public String dateOfEvent;
        public Integer decoratorId;
        public Integer customerId;

        public AddDecoratorBooking(String time, Integer price, Integer advance,
                                 Integer paid, String dateOfAdvance, String dateOfEvent, Integer decoratorId, Integer customerId
        ) {
            this.time = time;
            this.price = price;
            this.advance = advance;
            this.paid = paid;
            this.dateOfAdvance = dateOfAdvance;
            this.dateOfEvent = dateOfEvent;
            this.decoratorId = decoratorId;
            this.customerId = customerId;
        }
    }

    @Transactional
    public String requestBooking(AddDecoratorBooking request) {

        Decorators decorators = decoratorsRepository.findById(request.decoratorId)
                .orElseThrow(() -> new RuntimeException("Venue Not Found"));

        Users user = usersRepository.findById(request.customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DecoratorBooking booking = new DecoratorBooking(
                request.time,
                request.price,
                request.advance,
                request.paid,
                request.dateOfAdvance,
                request.dateOfEvent,
                decorators,
                user
        );

        decoratorBookingRepository.save(booking);

        return "Successfully Created the booking";
    }

    @Scheduled(cron = "59 59 23 * * ?")
    public void processDailyBookings() {

        List<DecoratorBooking> bookings = decoratorBookingRepository.findAll();

        List<Integer> ids = new ArrayList<>();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("h:mm a");

        for (DecoratorBooking booking : bookings) {
            String[] parts = booking.getTime().split("-");
            String endStr   = parts[1].trim();

            LocalTime endTime = LocalTime.parse(endStr, formatterTime);
            LocalDate eventDate = LocalDate.parse(booking.getDateOfEvent(), formatterDate);

            if (eventDate.isBefore(today) || (eventDate.isEqual(today) && now.isAfter(endTime))) {
                ids.add(booking.getId());
            }
        }

        if (!ids.isEmpty()) {
            decoratorBookingRepository.doneBooking(ids);
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
        decoratorBookingRepository.findById(request.id).orElseThrow(() -> new RuntimeException("Booking Not Found"));

        decoratorBookingRepository.processBooking(request.id);

        return request.msg;
    }

    public String cancelBooking(ConfirmBookingRequest request) {
        decoratorBookingRepository.findById(request.id).orElseThrow(() -> new RuntimeException("Booking Not Found"));

        decoratorBookingRepository.cancelBooking(request.id);

        return request.msg;
    }
}
