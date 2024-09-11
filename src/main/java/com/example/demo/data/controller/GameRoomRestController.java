package com.example.demo.data.controller;

import com.example.demo.data.entity.GameRoomEntity;
import com.example.demo.data.repository.GameRoomRepositoryImpl;
import com.example.demo.data.service.GameRoomService;
import com.example.demo.data.service.UserService;
import com.example.demo.security.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게임 진행을 총괄하는 api controller",
        description = "게임 시작, 진행 중 데이터를 저장하거나 가져오는 api controller")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameRoomRestController {
    final private GameRoomService gameRoomService;
    final private UserService userService;
    final private JwtProvider jwtProvider;
    private final GameRoomRepositoryImpl gameRoomRepositoryImpl;


    // GameRoom
    @Operation(operationId = "getReadyRoomId", summary = "게임 시작 전 방 설정 가져오기",
    description = "게임이 시작이 되기 전 방 설정 가져오는 메소드",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "로그인 되지 않은 상태에서의 요청 혹은 엑세스 토큰의 expire",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            )
    })
    @GetMapping("/game/room/ready")
    public ResponseEntity getReadyRoomId() {
        var readyGameRoom = gameRoomService.readReadyGR();
        if (readyGameRoom == null) {
            return new ResponseEntity("게임방이 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(readyGameRoom, HttpStatus.OK);
    }

    // GameRoom
    @Operation(operationId = "getStartedRoomId", summary = "게임이 진행 중인 게임룸 설정 가져오기",
    description = "게임이 시작한 후 진행 중인 방의 설정을 가져오는 메소드",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "로그인 되지 않은 상태에서의 요청 혹은 엑세스 토큰의 expire",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            )
    })
    @GetMapping("/game/room/start")
    public ResponseEntity getStartedRoomId() {
        var readyGameRoom = gameRoomService.readStartGR();
        if (readyGameRoom == null) {
            return new ResponseEntity("게임방이 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(readyGameRoom, HttpStatus.OK);
    }

    @Operation(operationId = "createGR", summary = "게임 방 생성 시키기",
            description = "방생성을 하고 기본 자금을 설정, 생성된 roomId 를 반환시키는 메소드",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "방 생성 성공",
                            content = @Content(
                                    schema = @Schema(type = "string", description = "생성된 방의 ID")
                            )
                    )
            }
    )
    @PostMapping("/game/room")
    public ResponseEntity<Long> createGR(@RequestParam String roomName, @RequestParam int budget, @RequestParam String jwt) {
        // JWT에서 userId 추출
        Long userId = Long.parseLong(jwtProvider.getClaimsFromToken(jwt).get("user_id"));
        var user = userService.getUser(userId);

        // GameRoomEntity 생성 및 설정
        var entity = GameRoomEntity.builder()
                .roomName(roomName)
                .budget(budget)
                .user(user)
                .roomStatus(0)  // 방 상태 설정
                .totPlayer(4)   // 총 플레이어 수 설정
                .build();

        // 게임 방 생성
        gameRoomService.createGR(entity);

        // 생성된 방 ID 반환
        return new ResponseEntity<>(entity.getRoomId(), HttpStatus.OK);
    }


    //게임방제목
    @Operation(operationId = "getRoomName", summary = "게임방 이름 가져오기",
    description = "roomId 를 보내서 해당 방의 이름을 가져오는 메소드.",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(type = "String", description = "방 이름")
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
                    description = "없는 roomId가 parameter 로 들어오는 에러, 하지만 내부적으로는 검증 후 요청 보낸다."
            )
    })
    @GetMapping("/roomName/{roomId}")
    public String getRoomName(@PathVariable Long roomId) {
        return gameRoomService.getRoomNameByRoomId(roomId);
    }

    @PutMapping("/game/room/image/{roomId}")
    public void roomImage(@PathVariable Long roomId, String roomImage){
        GameRoomEntity gameRoom = gameRoomRepositoryImpl.findByRoomId(roomId);
        gameRoom.setRoomImage(roomImage);

        gameRoomRepositoryImpl.save(gameRoom);
    }

    //lobby 방 삭제, 체크버튼
    @GetMapping("/game/myRoom/{roomId}/{userId}")
    public boolean roomCheck(@PathVariable Long roomId, @PathVariable Long userId){
        return gameRoomService.roomDeleteButton(roomId, userId);
    }


    @DeleteMapping("/game/room/delete/{roomId}/{userId}")
    public void roomDelete(@PathVariable Long roomId, @PathVariable Long userId){

        gameRoomService.roomDelete(roomId, userId);
    }

}
