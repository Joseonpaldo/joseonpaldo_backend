package com.example.demo.data.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.RankingService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "랭킹정보 반환하는 api controller", description = "랭킹 페이지에서 랭킹 정보 불러오는 api controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ranking")
public class RankingController {
    private final RankingService rankingService;

    // 2인용 랭킹
    @Operation(operationId = "getTopThree2p" , summary = "2인 게임 승률 탑3 불러오기",
    description = "2인 게임 승률 탑3 유저 정보 반환하는 controller",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "name",schema = @Schema(type = "String")),
                                    @SchemaProperty(name = "profileImage",schema = @Schema(type = "String")),
                                    @SchemaProperty(name = "winrate",schema = @Schema(type = "String"))
                            }
                            )
                    )
    })
    @GetMapping("/2p/top3")
    public List<Map<String, String>> getTopThree2p() {
        List<UserEntity> topThree2p = rankingService.getTopThree2p();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", topThree2p.get(i).getNickname());
            map.put("profileImage", topThree2p.get(i).getProfilePicture());
            map.put("winrate", String.valueOf(topThree2p.get(i).getWinRate2p()));
            result.add(map);
        }
        return result;
    }

    @Operation(operationId = "getFourToTen2p" , summary = "2인 게임 승률 4등~10등 불러오기",
            description = "2인 게임 승률 4등 ~ 10등 유저 정보 반환하는 controller",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schemaProperties = {
                                            @SchemaProperty(name = "name",schema = @Schema(type = "String")),
                                            @SchemaProperty(name = "profileImage",schema = @Schema(type = "String")),
                                            @SchemaProperty(name = "winrate",schema = @Schema(type = "String"))
                                    }
                            )
                    )
            })
    @GetMapping("/2p/fourToTen")
    public List<Map<String, String>> getFourToTen2p() {
        List<UserEntity> fourToTen2p = rankingService.getFourToTen2p();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(fourToTen2p.get(i).getUserId()));
            map.put("name", fourToTen2p.get(i).getNickname());
            map.put("profileImage", fourToTen2p.get(i).getProfilePicture());
            map.put("winrate", String.valueOf(fourToTen2p.get(i).getWinRate2p()));
            map.put("play", String.valueOf(fourToTen2p.get(i).getTot2p()));
            result.add(map);
        }
        return result;
    }

    // 4인용 랭킹
    @Operation(operationId = "getTopThree4p" , summary = "4인 게임 승률 탑3 불러오기",
            description = "4인 게임 승률 탑3 유저 정보 반환하는 controller",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schemaProperties = {
                                            @SchemaProperty(name = "name",schema = @Schema(type = "String")),
                                            @SchemaProperty(name = "profileImage",schema = @Schema(type = "String")),
                                            @SchemaProperty(name = "winrate",schema = @Schema(type = "String"))
                                    }
                            )
                    )
            })
    @GetMapping("/4p/top3")
    public List<Map<String, String>> getTopThree4p() {
        List<UserEntity> topThree4p = rankingService.getTopThree4p();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", topThree4p.get(i).getNickname());
            map.put("profileImage", topThree4p.get(i).getProfilePicture());
            map.put("winrate", String.valueOf(topThree4p.get(i).getWinRate2p()));
            result.add(map);
        }
        return result;
    }

    @Operation(operationId = "getFourToTen4p" , summary = "4인 게임 승률 4등~10등까지 불러오기",
            description = "4인 게임 승률 4등 ~ 10등 유저 정보 반환하는 controller",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schemaProperties = {
                                            @SchemaProperty(name = "name",schema = @Schema(type = "String")),
                                            @SchemaProperty(name = "profileImage",schema = @Schema(type = "String")),
                                            @SchemaProperty(name = "winrate",schema = @Schema(type = "String"))
                                    }
                            )
                    )
            })
    @GetMapping("/4p/fourToTen")
    public List<Map<String, String>> getFourToTen4p() {
        List<UserEntity> fourToTen4p = rankingService.getFourToTen4p();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(fourToTen4p.get(i).getUserId()));
            map.put("name", fourToTen4p.get(i).getNickname());
            map.put("profileImage", fourToTen4p.get(i).getProfilePicture());
            map.put("winrate", String.valueOf(fourToTen4p.get(i).getWinRate2p()));
            map.put("play", String.valueOf(fourToTen4p.get(i).getTot2p()));
            result.add(map);
        }
        return result;
    }
}
