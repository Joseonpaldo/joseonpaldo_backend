package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "friend_relation", indexes = {
        @Index(name = "idx_friend_relation_user_ids", columnList = "user_id_1, user_id_2")
})
public class FriendRelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id_1")
    private Long userId1;
    @Column(name = "user_id_2")
    private Long userId2;

}