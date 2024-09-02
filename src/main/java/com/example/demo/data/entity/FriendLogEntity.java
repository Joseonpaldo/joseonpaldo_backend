package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "friend_log")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FriendLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "from_id")
    private String fromId;
    @Column(name = "to_id")
    private String toId;
    @Column(name = "chat_log")
    private String chatLog;
    @Column(name = "msg_time")
    private Timestamp msgTime;
    @Column(name="f_chat_room_id")
    private Long fChatRoomId;

    @PrePersist
    protected void onCreate() {
        // 현재 한국 시간을 가져와서 msg_time에 설정
        this.msgTime = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }
}
