package com.example.demo.data.service;

import com.example.demo.data.entity.GameRoomEntity;
import com.example.demo.data.repository.GameRoomRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameRoomService {
    final private GameRoomRepositoryImpl impl;
    private final GameRoomRepositoryImpl gameRoomRepositoryImpl;


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
        impl.save(entity);
    }


    //게임방제목
    public String getRoomNameByRoomId(Long roomId) {
        GameRoomEntity gameRoom = impl.findByRoomId(roomId);
        if (gameRoom != null) {
            return gameRoom.getRoomName();
        } else {
            return null;
        }
    }
}
