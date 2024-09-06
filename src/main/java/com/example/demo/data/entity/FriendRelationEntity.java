package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "friend_relation")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FriendRelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id_1")
    private Long userId1;
    @Column(name = "user_id_2")
    private Long userId2;

}