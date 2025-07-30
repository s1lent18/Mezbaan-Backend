package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.ChatRooms;
import com.example.Mezbaan.database.models.Users;
import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.repository.ChatRoomRepository;
import com.example.Mezbaan.database.repository.UsersRepository;
import com.example.Mezbaan.database.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public Optional<Integer> getChatRoomId(Integer senderId, Integer recipientId, boolean createIfNotExist) {
        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .or(() -> chatRoomRepository.findBySenderIdAndRecipientId(recipientId, senderId))
                .map(ChatRooms::getId)
                .or(() -> {
                    if (createIfNotExist) {
                        Integer chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private Integer createChatId(Integer senderId, Integer recipientId) {

        Users user = usersRepository.findById(senderId).orElseThrow(() -> new RuntimeException("User Not Found"));

        Vendor vendor = vendorRepository.findById(recipientId).orElseThrow(() -> new RuntimeException("Vendor Not Found"));

        ChatRooms newRoom = ChatRooms.builder()
                .sender(user)
                .recipient(vendor)
                .build();
        ChatRooms saved = chatRoomRepository.save(newRoom);
        return saved.getId();
    }
}
