package com.example.demo.data.contoller;

import com.example.demo.data.entity.GameRoomEntity;
import com.example.demo.data.service.GameRoomService;
import com.example.demo.data.service.UserAccountService;
import com.example.demo.social.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameRoomRestController {
    final private GameRoomService gameRoomService;
    final private UserAccountService userService;
    final private JwtProvider jwtProvider;


    // GameRoom
    @GetMapping("/game/room/ready")
    public ResponseEntity getMethodName() {
        var readyGameRoom = gameRoomService.readReadyGR();
        if (readyGameRoom == null) {
            return new ResponseEntity("게임방이 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(readyGameRoom, HttpStatus.OK);
    }

    @PostMapping("/game/room")
    public ResponseEntity createGR(@RequestParam String roomName, @RequestParam int budget, @RequestParam String jwt) {
        var userId = jwtProvider.getUserIdByJWT(jwt);
        var user = userService.readUser(Long.valueOf(userId));

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

    //id : room_id
    @PutMapping("/game/room/{id}")
    public void putMethodName(@PathVariable("id") Long room_id, @RequestBody GameRoomEntity entity) {
        gameRoomService.updateGR(room_id, entity);
    }

    @DeleteMapping("/game/room")
    public void deleteMethodName(@RequestParam Long room_id) {
        gameRoomService.deleteGR(room_id);
    }




}
