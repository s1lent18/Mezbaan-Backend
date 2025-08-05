package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.VenueBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueBookingRepository extends JpaRepository<VenueBooking, Integer> {

}