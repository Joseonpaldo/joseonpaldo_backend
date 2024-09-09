package com.example.demo.data.controller;

import com.example.demo.data.dto.ChattingDto;
import com.example.demo.data.entity.ChatMessageEntity;
import com.example.demo.data.entity.ChatRoomEntity;
import com.example.demo.data.service.ChatMessageService;
import com.example.demo.data.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class ChattingController {


    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatMessageService chatMessageService;


    // 채팅방 생성 또는 조회
    @PostMapping("/chat/createOrGetChatRoom")
    public ResponseEntity<?> createOrGetChatRoom(@RequestBody Map<String, Long> users) {
        Long userId1 = users.get("userId1");
        Long userId2 = users.get("userId2");

        ChatRoomEntity chatRoom = chatRoomService.createOrGetChatRoom(userId1, userId2);

        System.out.println(chatRoom.getRoomId());
        return ResponseEntity.ok().body(Map.of("roomId", chatRoom.getRoomId()));
    }

    // 특정 채팅방의 모든 메시지 조회
    @GetMapping("/room/{chatRoomId}/messages")
    public ResponseEntity<?> getMessagesByChatRoomId(@PathVariable Long chatRoomId) {
        List<ChatMessageEntity> messages = chatMessageService.getMessagesByChatRoomId(chatRoomId);

        System.out.println("조회된 메시지: " + messages); // 메시지 확인을 위한 로그 추가

        // 메시지가 존재하는지 확인
        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/chat/history")
    public ResponseEntity<?> getChatHistory(@RequestParam Long roomId) {
        // 채팅방 메시지 리스트 가져오기
        List<ChatMessageEntity> chatMessages = chatMessageService.getMessagesByChatRoomId(roomId);

        // ChatMessageEntity를 ChattingDto로 변환
        List<ChattingDto> chatHistory = chatMessages.stream()
                .map(message -> {
                    Long receiverId = message.getSender().getUserId().equals(message.getChatRoom().getFriendRelation().getUserId1())
                            ? message.getChatRoom().getFriendRelation().getUserId2()
                            : message.getChatRoom().getFriendRelation().getUserId1();

                    return ChattingDto.builder()
                            .senderId(message.getSender().getUserId())
                            .receiverId(receiverId)
                            .userId1(message.getChatRoom().getFriendRelation().getUserId1())
                            .userId2(message.getChatRoom().getFriendRelation().getUserId2())
                            .messageContent(message.getMessageContent())
                            .timestamp(message.getSentAt())
                            .build();
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(chatHistory);
    }



}