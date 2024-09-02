package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "friend_relation")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FriendRelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_1", referencedColumnName = "userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "friend_id", referencedColumnName = "userId")
    private UserEntity friend;
}
