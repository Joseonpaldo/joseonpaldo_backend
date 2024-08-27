package com.example.demo.data.service;

import com.example.demo.data.entity.GameRoomEntity;
import com.example.demo.data.repository.GameRoomRepository;
import com.example.demo.data.repository.inter.GameRoomRepositoryImpl;
import com.example.demo.social.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameRoomService {
    final private GameRoomRepository grRepo;
    final private GameRoomRepositoryImpl impl;


    //시작 전 게임방
    public List<GameRoomEntity> readReadyGR() {
        return impl.findAllByRoomStatus(0);
    }

    //게임 중 게임방
    public List<GameRoomEntity> readStartGR() {
        return impl.findAllByRoomStatus(1);
    }

    //게임 끝난 게임방
    public List<GameRoomEntity> readEndGR() {
        return impl.findAllByRoomStatus(2);
    }


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


    // DELETE
    public void deleteGR(Long room_id) {
        grRepo.deleteGR(room_id);
    }
}
