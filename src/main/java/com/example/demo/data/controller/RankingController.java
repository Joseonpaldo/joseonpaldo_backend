package com.example.demo.data.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.RankingService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ranking")
public class RankingController {
    private final RankingService rankingService;

    // 2인용 랭킹
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
    @GetMapping("/2p/fourToTen")
    public List<Map<String, String>> getFourToTen2p() {
        List<UserEntity> fourToTen2p = rankingService.getFourToTen2p();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 3; i < 10; i++) {
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
    @GetMapping("/4p/fourToTen")
    public List<Map<String, String>> getFourToTen4p() {
        List<UserEntity> fourToTen4p = rankingService.getFourToTen4p();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 3; i < 10; i++) {
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
