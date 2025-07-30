package com.example.Mezbaan.database.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "chatmessages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chatid", referencedColumnName = "id", nullable = false)
    private ChatRooms chatRoom;

    @ManyToOne
    @JoinColumn(name = "senderid", referencedColumnName = "id", nullable = false)
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "recipientid", referencedColumnName = "id", nullable = false)
    private Vendor recipient;

    private String content;

    @Column(name = "timestamp")
    private Timestamp timestamp;
}
