package com.example.Mezbaan.controller;

import com.example.Mezbaan.database.models.*;
import com.example.Mezbaan.service.ChatMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessagesService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessages chatMessage) {
        ChatMessages savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getRecipient().getId()), "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSender().getId(),
                        savedMsg.getRecipient().getId(),
                        savedMsg.getContent()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessages>> findChatMessages(@PathVariable Integer senderId,
                                                               @PathVariable Integer recipientId) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @MessageMapping("/chat.sendMessage")
    @SendToUser("/queue/messages")
    public ChatMessages sendMessage(@Payload ChatMessages message) {
        return message;
    }

    @MessageMapping("/vendor.connect")
    @SendTo("/vendor/public")
    public Vendor connectVendor(@Payload Vendor vendor) {
        vendor.setChatStatus(Status.ONLINE);
        return vendor;
    }

    @MessageMapping("/user.connect")
    @SendTo("/user/public")
    public Users connectUser(@Payload Users user) {
        user.setStatus(Status.ONLINE);
        return user;
    }
}