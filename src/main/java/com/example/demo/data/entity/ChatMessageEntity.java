package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoomEntity chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender; // senderId 대신 UserEntity로 설정

    @Column(nullable = false, length = 1000)
    private String messageContent;

    @Column(nullable = false)
    private LocalDateTime sentAt;
}
