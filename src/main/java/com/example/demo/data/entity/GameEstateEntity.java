package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//부동산
@Entity
@Table(name = "game_estate")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameEstateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estateId;
    private String name;
    private int price;
}
