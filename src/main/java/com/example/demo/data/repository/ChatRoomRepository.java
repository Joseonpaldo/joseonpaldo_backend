package com.example.demo.data.repository;

import com.example.demo.data.entity.ChatRoomEntity;
import com.example.demo.data.entity.FriendRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    // 특정 친구 관계를 기반으로 채팅방을 조회
    Optional<ChatRoomEntity> findByFriendRelation(FriendRelationEntity friendRelation);
}
