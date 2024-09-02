package com.example.demo.data.controller;

import com.example.demo.data.entity.GameRoomEntity;
import com.example.demo.data.service.GameRoomService;
import com.example.demo.data.service.UserService;
import com.example.demo.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameRoomRestController {
    final private GameRoomService gameRoomService;
    final private UserService userService;
    final private JwtProvider jwtProvider;


    // GameRoom
    @GetMapping("/game/room/ready")
    public ResponseEntity getReadyRoomId() {
        var readyGameRoom = gameRoomService.readReadyGR();
        if (readyGameRoom == null) {
            return new ResponseEntity("게임방이 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(readyGameRoom, HttpStatus.OK);
    }

    // GameRoom
    @GetMapping("/game/room/start")
    public ResponseEntity getStartRoomId() {
        var readyGameRoom = gameRoomService.readStartGR();
        if (readyGameRoom == null) {
            return new ResponseEntity("게임방이 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(readyGameRoom, HttpStatus.OK);
    }

    @PostMapping("/game/room")
    public ResponseEntity createGR(@RequestParam String roomName, @RequestParam int budget, @RequestParam String jwt) {
        Long userId = Long.parseLong(jwtProvider.getClaimsFromToken(jwt).get("user_id"));
        var user = userService.getUser(userId);

        var entity = GameRoomEntity.builder()
                .room_name(roomName)
                .budget(budget)
                .user(user)
                .roomStatus(0)
                .tot_player(4)
                .build();
        gameRoomService.createGR(entity);

        return new ResponseEntity(entity.getRoom_id(), HttpStatus.OK);
    }



}
