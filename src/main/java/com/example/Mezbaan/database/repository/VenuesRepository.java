package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Venues;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenuesRepository extends JpaRepository<Venues, Integer> {

    @NonNull
    Page<Venues> findAll(@NonNull Pageable pageable);
}