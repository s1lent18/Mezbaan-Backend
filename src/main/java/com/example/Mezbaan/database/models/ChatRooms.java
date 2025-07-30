package com.example.Mezbaan.database.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatrooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "senderid", referencedColumnName = "id", nullable = false)
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "recipientid", referencedColumnName = "id", nullable = false)
    private Vendor recipient;
}
