package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query(value = """
            SELECT * FROM users
            WHERE EMAIL = :email
            """, nativeQuery = true)
    public Optional<Users> findByEmail(String email);

}
