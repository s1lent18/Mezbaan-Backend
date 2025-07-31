package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Decorators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecoratorsRepository extends JpaRepository<Decorators, Integer> {
}
