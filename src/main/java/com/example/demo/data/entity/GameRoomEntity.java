package com.example.demo.data.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @ColumnDefault("0")
    private int tot_player;
    @ColumnDefault("0")
    private int curr_player;
    private int budget;
}
