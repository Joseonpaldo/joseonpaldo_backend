package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

//채팅
@Entity
@Table(name = "game_log")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomId")
    private GameRoomEntity gameRoom;
    private int type;
    private String message;
    private Timestamp createdAt;
}
