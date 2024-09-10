package com.example.demo.data.controller;

import com.example.demo.data.dto.ChattingDto;
import com.example.demo.data.entity.ChatMessageEntity;
import com.example.demo.data.entity.ChatRoomEntity;
import com.example.demo.data.service.ChatMessageService;
import com.example.demo.data.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name="친구 채팅 api controller", description = "1대1 친구 채팅방을 불러오는 method 들이 있는 api controller")
public class ChattingController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @Operation(operationId = "createOrGetChatRoom",summary = "1대1 친구 채팅방을 만들거나 가져오기",
    description = "유저아이디와 친구아이디를 가지고 있는 룸을 조회하여 roomId가 담긴 dto 반환 조회되지 않으면 새로 생성한 roomId를 담아서 dto 를 반환한다.",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "roomId 반환 성공",
                    content = @Content(schemaProperties = {
                            @SchemaProperty(name="ChattingDto", schema = @Schema(implementation = ChattingDto.class))
                    })
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "로그인 되지 않은 상태에서의 요청 혹은 엑세스 토큰의 expire",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            )
    })
    @PostMapping("/chat/createOrGetChatRoom")
    public ResponseEntity<?> createOrGetChatRoom(@RequestBody ChattingDto requestDto) {
        ChatRoomEntity chatRoom = chatRoomService.createOrGetChatRoom(requestDto.getUserId1(), requestDto.getUserId2());
        return ResponseEntity.ok(new ChattingDto(chatRoom.getRoomId()));
    }

    @Operation(operationId = "getMessages",summary = "친구와의 채팅 기록을 불러오기",
    description = "roomId를 받아서 해당 roomId에 배정되있는 친구와의 채팅 기록을 불러오기",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "채팅기록 반환 성공",
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "ChattingDto",
                                            schema = @Schema(
                                            type = "array",
                                            implementation = ChattingDto.class))
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "로그인 되지 않은 상태에서의 요청 혹은 엑세스 토큰의 expire",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "존재하지 않는 roomId, 이미 roomId 검증은 createOrGetChatRoom 메소드에서 마쳤기 때문에 postman 등으로 요청을 보내는 것이 아닌한 날 일이 없음."
            ,content = @Content(
                    schema = @Schema(implementation = void.class)
            )
            )
    })
    @GetMapping("/room/{roomId}/messages")
    public List<ChattingDto> getMessages(@PathVariable Long roomId) {
        // 채팅방 ID로 채팅방을 조회
        ChatRoomEntity chatRoom = chatRoomService.getChatRoomById(roomId);
        // 해당 채팅방의 모든 메시지를 반환

        System.out.println("chatroom" + chatRoom);
        return chatMessageService.getMessagesByChatRoom(chatRoom);
    }
}
