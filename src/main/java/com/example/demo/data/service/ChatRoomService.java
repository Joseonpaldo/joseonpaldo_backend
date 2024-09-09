package com.example.demo.data.service;

import com.example.demo.data.entity.ChatRoomEntity;
import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.repository.ChatRoomRepository;
import com.example.demo.data.repository.FriendRelationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private FriendRelationRepositoryImpl friendRelationRepository;

    // 특정 친구 관계에 기반하여 채팅방을 생성하거나 기존 채팅방을 반환
    public ChatRoomEntity createOrGetChatRoom(Long userId1, Long userId2) {
        // 친구 관계 조회
        FriendRelationEntity friendRelation = friendRelationRepository.findFriendRelation(userId1, userId2)
                .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));

        // 해당 친구 관계로 채팅방 조회, 없으면 생성
        return chatRoomRepository.findByFriendRelation(friendRelation)
                .orElseGet(() -> {
                    ChatRoomEntity newRoom = ChatRoomEntity.builder()
                            .friendRelation(friendRelation)
                            .isActive(true)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });
    }

}

