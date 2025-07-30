package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.ChatMessages;
import com.example.Mezbaan.database.models.ChatRooms;
import com.example.Mezbaan.database.repository.ChatMessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessagesService {

    @Autowired
    private ChatMessagesRepository chatMessagesRepository;

    @Autowired
    private ChatRoomService chatRoomService;

    public ChatMessages save(ChatMessages chatMessage) {

        ChatRooms chatRooms = chatMessage.getChatRoom();

        chatRoomService.getChatRoomId(chatMessage.getSender().getId(), chatMessage.getRecipient().getId(), true)
                .orElseThrow();
        chatMessage.setChatRoom(chatRooms);
        chatMessage.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return chatMessagesRepository.save(chatMessage);
    }

    public List<ChatMessages> findChatMessages(Integer senderId, Integer recipientId) {
        Optional<Integer> chatIdOpt = chatRoomService.getChatRoomId(senderId, recipientId, false);
        if (chatIdOpt.isPresent()) {
            return chatMessagesRepository.findByChatRoom_Id(chatIdOpt.get());
        }
        return List.of();
    }
}
