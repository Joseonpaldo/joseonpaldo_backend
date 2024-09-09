package com.example.demo.data.repository;

import com.example.demo.data.entity.ChatMessageEntity;
import com.example.demo.data.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    // 특정 채팅방의 모든 메시지를 시간 순으로 조회
    List<ChatMessageEntity> findByChatRoomRoomIdOrderBySentAtAsc(Long chatRoomId);

}
