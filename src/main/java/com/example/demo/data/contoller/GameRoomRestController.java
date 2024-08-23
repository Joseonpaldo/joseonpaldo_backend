package com.example.demo.data.contoller;

import com.example.demo.data.entity.GameRoomEntity;
import com.example.demo.data.service.GameRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameRoomRestController {
    GameRoomService gameRoomService;

    // GameRoom
    @GetMapping("/game/room")
    public GameRoomEntity getMethodName(@RequestParam Long room_id) {
        return gameRoomService.readGR(room_id);
    }

    @PostMapping("/game/room")
    public void postMethodName(@RequestBody GameRoomEntity entity) {
        gameRoomService.createGR(entity);
    }

    //id : room_id
    @PutMapping("/game/room/{id}")
    public void putMethodName(@PathVariable("id") Long room_id, @RequestBody GameRoomEntity entity) {
        gameRoomService.updateGR(room_id, entity);
    }

    @PutMapping("/game/room/manager")
    public void putMethodName(@RequestParam Long room_id,
                              @RequestParam Long user_id) {
        gameRoomService.updateGRManager(room_id, user_id);
    }

    @DeleteMapping("/game/room")
    public void deleteMethodName(@RequestParam Long room_id) {
        gameRoomService.deleteGR(room_id);
    }
}
