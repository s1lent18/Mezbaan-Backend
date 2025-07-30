package com.example.Mezbaan.database.repository;

import com.example.Mezbaan.database.models.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessages, Integer> {
    List<ChatMessages> findByChatRoom_Id(Integer chatId);
}
