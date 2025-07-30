package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {

}
