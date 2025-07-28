package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    @Query(
        value = """
                SELECT * FROM vendors
                WHERE EMAIL = :email
                """, nativeQuery = true
    )
    Optional<Vendor> findByEmail(String email);
}
