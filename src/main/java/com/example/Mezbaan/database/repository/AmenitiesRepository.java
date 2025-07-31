package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Integer> {

}
