package com.example.demo.data.controller;

import com.example.demo.data.repository.GameDataRepositoryImpl;
import com.example.demo.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameDataRestController {
    final private GameDataRepositoryImpl gameDataRepository;
    final private JwtProvider jwtProvider;

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
