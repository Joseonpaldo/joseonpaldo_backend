package com.example.demo.data.service;

import com.example.demo.data.dto.ChattingDto;
import com.example.demo.data.entity.ChatMessageEntity;
import com.example.demo.data.entity.ChatRoomEntity;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.ChatMessageRepository;
import com.example.demo.data.repository.UserRepositoryImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepositoryImpl userRepository;  // UserEntity를 가져오기 위한 Repository 추가

    // 메시지 저장 서비스
    @Transactional
    public ChatMessageEntity saveMessage(ChatRoomEntity chatRoom, Long senderId, String messageContent) {
        // senderId로 UserEntity 조회
        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. senderId: " + senderId));

        ChatMessageEntity message = ChatMessageEntity.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .messageContent(messageContent)
                .sentAt(LocalDateTime.now())
                .build();

        return chatMessageRepository.save(message);
    }


    // 특정 채팅방의 모든 메시지 불러오기
    @Transactional
    public List<ChattingDto> getMessagesByChatRoom(ChatRoomEntity chatRoom) {
        List<ChatMessageEntity> messages=chatMessageRepository.findByChatRoom(chatRoom);
        return messages.stream().map(ChatMessageEntity::toDto).collect(Collectors.toList());
    }
}

