package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.ChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRooms, Long> {
    Optional<ChatRooms> findBySenderIdAndRecipientId(Integer senderId, Integer recipientId);
}