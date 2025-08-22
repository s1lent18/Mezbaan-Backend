package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.VenueBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueBookingRepository extends JpaRepository<VenueBooking, Integer> {

    @Query(
            value = """
                    UPDATE VENUEBOOKING
                    SET STATUS = 'CONFIRMED'
                    WHERE ID = :id
                    """,
            nativeQuery = true
    )
    String processBooking(Integer id);

    @Query(
            value = """
                    UPDATE VENUEBOOKING
                    SET STATUS = 'CANCELED'
                    WHERE ID = :id
                    """,
            nativeQuery = true
    )
    String cancelBooking(Integer id);

    @Modifying
    @Query(
            value = """
                    UPDATE VENUEBOOKING
                    SET STATUS = 'DONE'
                    WHERE ID = (:ids)
                    """,
            nativeQuery = true
    )
    Integer doneBooking(@Param("ids") List<Integer> id);

}