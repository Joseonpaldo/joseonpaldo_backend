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
    private Long room_id;
    private String room_name;
    @ColumnDefault("0")
    private Integer roomStatus;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;
    @ColumnDefault("4")
    private int tot_player; //방 생성 시 본인 필수
    @ColumnDefault("0")
    private int curr_player;
    private int budget;
}
