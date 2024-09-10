package com.example.demo.data.controller;

import com.example.demo.data.dto.UserStatsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.dto.UserPrintDto;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;
import com.example.demo.security.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "유저정보 관리하는 api controller", description = "유저정보를 조회하거나 유저임을 검증하는 api controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    public UserEntity getUser(@RequestParam("user_id") Long user_id) {
        System.out.println("i am in here");
        return userService.getUser(user_id);
    }

    @Operation(operationId = "confirmUserData", summary = "user 정보가 security 검증을 통과해서 잘 처리 되는지 확인하기",
    description = "해당 메소드까지 요청을 성공해서 ok가 반환된다는 것은 security 검증을 통과했다는 것을 입증하는 메소드",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(
                                    type = "string",
                                    example = "ok"
                            )
            )
            )
    })
    @GetMapping("/user/data")
    public ResponseEntity getUserData(HttpServletRequest request) {
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @Operation(operationId = "getUserByJWT", summary = "jwt 를 보내 유저 정보를 받기",
    description = "유저의 엑세스 토큰을 받아서 유저 정보를 반환시키는 메소드",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = UserPrintDto.class)
                    )
            )
    })
    @GetMapping("/user/{jwt}")
    public UserPrintDto getUserByJWT(@PathVariable("jwt") String jwt) {
        Long userId = Long.parseLong(jwtProvider.getClaimsFromToken(jwt).get("user_id"));
        return userService.findUserPrintById(userId);
    }


    // userId를 통해 유저 정보 조회
    @Operation(operationId = "getUserStats", summary = "user_id 를 보내 유저 승률정보를 받기",
            description = "유저의 인덱스를 받아서 유저 승률정보를 반환시키는 메소드",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = UserPrintDto.class)
                            )
                    )
            })
    @GetMapping("/userinfor/{userId}")
    public ResponseEntity<UserStatsDto> getUserStats(@PathVariable Long userId) {
        UserEntity user = userService.getUser(userId);

        if (user == null || user.getUserId() == null) {
            return ResponseEntity.notFound().build();
        }

        UserStatsDto userStats = UserStatsDto.builder()
                .nickname(user.getNickname())
                .tot2p(user.getTot2p())
                .win2p(user.getWin2p())
                .tot4p(user.getTot4p())
                .win4p(user.getWin4p())
                .winRate2p(user.getWinRate2p())
                .winRate4p(user.getWinRate4p())
                .build();

        return ResponseEntity.ok(userStats);
    }

}
