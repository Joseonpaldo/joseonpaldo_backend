package com.example.demo.data.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long log_id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    private GameRoomEntity gameRoom;
    private int type;
    private String message;
    private Timestamp created_at;
}
