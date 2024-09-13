package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "game_data")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomId")
    private GameRoomEntity gameRoom;
    private int myTurn;
    private String avatar;
}
