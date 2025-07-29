package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Photographers;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographersRepository extends JpaRepository<Photographers, Integer> {

    @NonNull
    Page<Photographers> findAll(@NonNull Pageable pageable);
}