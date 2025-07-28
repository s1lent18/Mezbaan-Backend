package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Venues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenuesRepository extends JpaRepository<Venues, Integer> {

}