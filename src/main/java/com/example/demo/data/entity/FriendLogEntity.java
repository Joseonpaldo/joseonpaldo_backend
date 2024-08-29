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
    private String from_id;
    @Column(name = "to_id")
    private String to_id;
    @Column(name = "chat_log")
    private String chat_log;
    @Column(name = "msg_time")
    private Timestamp msg_time;

    @PrePersist
    protected void onCreate() {
        // 현재 한국 시간을 가져와서 msg_time에 설정
        this.msg_time = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }
}
