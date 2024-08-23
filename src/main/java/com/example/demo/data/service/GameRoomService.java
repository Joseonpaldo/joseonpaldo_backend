package com.example.demo.data.service;

import com.example.demo.data.entity.GameRoomEntity;
import com.example.demo.data.repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameRoomService {
    final private GameRoomRepository grRepo;

    // GameRoom
    // CREATE
    public void createGR(GameRoomEntity entity) {
        grRepo.createGR(entity);
    }

    // READ
    public GameRoomEntity readGR(Long room_id) {
        return grRepo.readGR(room_id);
    }

    // UPDATE
    public void updateGR(Long room_id, GameRoomEntity entity) {
        GameRoomEntity room = grRepo.readGR(room_id);
        // 변환내용 적용
        room.setBudget(entity.getBudget());
        room.setCurr_player(entity.getCurr_player());
        room.setTot_player(entity.getTot_player());

        grRepo.updateGR(entity);

    }

    public void updateGRManager(Long room_id, Long user_id){
        GameRoomEntity room = grRepo.readGR(room_id);
        //바뀔 방장
        room.setUser_id(user_id);
        grRepo.updateGRManager(room);
    }

    // DELETE
    public void deleteGR(Long room_id) {
        grRepo.deleteGR(room_id);
    }
}
