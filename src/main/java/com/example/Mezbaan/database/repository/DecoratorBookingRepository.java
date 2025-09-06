package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.DecoratorBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DecoratorBookingRepository extends JpaRepository<DecoratorBooking, Integer> {

    @Modifying
    @Query(
            value = """
                    UPDATE DECORATORBOOKING
                    SET STATUS = 'CONFIRMED'
                    WHERE ID = :id
                    """,
            nativeQuery = true
    )
    void processBooking(Integer id);

    @Modifying
    @Query(
            value = """
                    UPDATE DECORATORBOOKING
                    SET STATUS = 'CANCELED'
                    WHERE ID = :id
                    """,
            nativeQuery = true
    )
    void cancelBooking(Integer id);

    @Modifying
    @Query(
            value = """
                    UPDATE DECORATORBOOKING
                    SET STATUS = 'DONE'
                    WHERE ID = (:ids)
                    """,
            nativeQuery = true
    )
    void doneBooking(@Param("ids") List<Integer> id);
}
