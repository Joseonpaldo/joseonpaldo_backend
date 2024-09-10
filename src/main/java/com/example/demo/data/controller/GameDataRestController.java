package com.example.demo.data.controller;

import com.example.demo.data.repository.GameDataRepositoryImpl;
import com.example.demo.security.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게임 중 유저의 데이터를 불러오는 api controller",
        description = "게임 중이나 게임 재접속을 하는 유저의 데이터를 불러오는 method 가 있는 api controller")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameDataRestController {
    final private GameDataRepositoryImpl gameDataRepository;
    final private JwtProvider jwtProvider;

    @Operation(operationId = "getPlayerNum",summary = "게임 순서 불러오기",
    description = "자신의 게임 순서 몇번째인지 불러오는 메소드",
            security = {@SecurityRequirement(name = "custom-auth-token")},
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게임 순서 불러오기 성공",
                    content = @Content(
                            schema = @Schema(implementation = Integer.class)
                    )
            )
    })
    @GetMapping("/game/data/player")
    public ResponseEntity getPlayerNum(@RequestParam Long roomName, @RequestParam String jwt) {
        Long userId = Long.parseLong(jwtProvider.getClaimsFromToken(jwt).get("user_id"));
        var order = gameDataRepository.findOrderByRoomIdAndUserId(roomName, Long.valueOf(userId));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }
}
