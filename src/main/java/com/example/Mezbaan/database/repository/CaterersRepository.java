package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Caterers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaterersRepository extends JpaRepository<Caterers, Integer> {

}