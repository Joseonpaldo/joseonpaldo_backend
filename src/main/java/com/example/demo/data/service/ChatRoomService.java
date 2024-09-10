package com.example.demo.data.service;


import com.example.demo.data.entity.ChatRoomEntity;
import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.repository.ChatRoomRepository;
import com.example.demo.data.repository.FriendRelationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private FriendRelationRepositoryImpl friendRelationRepository;

    // 특정 친구 관계에 기반하여 채팅방을 생성하거나 기존 채팅방을 반환
    public ChatRoomEntity createOrGetChatRoom(Long userId1, Long userId2) {
        // 양방향 친구 관계 검색
        Optional<FriendRelationEntity> friendRelationOpt = friendRelationRepository.findByUserId1AndUserId2OrUserId2AndUserId1(userId1, userId2, userId1, userId2);

        // 친구 관계가 존재하지 않을 경우 새로 생성
        FriendRelationEntity friendRelation = friendRelationOpt.orElseGet(() -> {
            FriendRelationEntity newFriendRelation = FriendRelationEntity.builder()
                    .userId1(userId1)
                    .userId2(userId2)
                    .build();
            return friendRelationRepository.save(newFriendRelation);
        });

        // 해당 친구 관계에 기반한 채팅방이 없으면 새로 생성
        return chatRoomRepository.findByFriendRelation(friendRelation)
                .orElseGet(() -> {
                    ChatRoomEntity newRoom = ChatRoomEntity.builder()
                            .friendRelation(friendRelation)
                            .isActive(true)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });
    }

    // ID를 기반으로 채팅방을 조회
    public ChatRoomEntity getChatRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. 채팅방 ID: " + roomId));
    }
}
