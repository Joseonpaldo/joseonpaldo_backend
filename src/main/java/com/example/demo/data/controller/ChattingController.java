package com.example.demo.data.controller;

import com.example.demo.data.dto.ChattingDto;
import com.example.demo.data.entity.ChatMessageEntity;
import com.example.demo.data.entity.ChatRoomEntity;
import com.example.demo.data.service.ChatMessageService;
import com.example.demo.data.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChattingController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @PostMapping("/chat/createOrGetChatRoom")
    public ResponseEntity<?> createOrGetChatRoom(@RequestBody ChattingDto requestDto) {
        ChatRoomEntity chatRoom = chatRoomService.createOrGetChatRoom(requestDto.getUserId1(), requestDto.getUserId2());
        return ResponseEntity.ok(new ChattingDto(chatRoom.getRoomId()));
    }

    @GetMapping("/room/{roomId}/messages")
    public List<ChattingDto> getMessages(@PathVariable Long roomId) {
        // 채팅방 ID로 채팅방을 조회
        ChatRoomEntity chatRoom = chatRoomService.getChatRoomById(roomId);
        // 해당 채팅방의 모든 메시지를 반환

        System.out.println("chatroom" + chatRoom);
        return chatMessageService.getMessagesByChatRoom(chatRoom);
    }
}
