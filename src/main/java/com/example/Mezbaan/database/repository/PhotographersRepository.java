package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Photographers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographersRepository extends JpaRepository<Photographers, Integer> {

}