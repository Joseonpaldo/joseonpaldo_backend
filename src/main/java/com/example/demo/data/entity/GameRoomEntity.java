package com.example.demo.data.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "game_room")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String roomName;
    @ColumnDefault("0")
    private Integer roomStatus;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserEntity user;
    @ColumnDefault("4")
    private int totPlayer; //방 생성 시 본인 필수
    @ColumnDefault("0")
    private int currPlayer;
    private int budget;
    private String currentMap;
}
