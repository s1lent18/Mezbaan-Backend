package com.example.Mezbaan.database.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatNotification {
    private Integer id;
    private Integer senderId;
    private Integer recipientId;
    private String content;

    public ChatNotification() {}

    public ChatNotification(
            Integer id, Integer senderId, Integer recipientId, String content
    ) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
    }
}