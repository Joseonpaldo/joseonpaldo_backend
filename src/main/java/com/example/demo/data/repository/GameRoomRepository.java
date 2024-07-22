package com.example.demo.data.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.data.entity.GameRoomEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GameRoomRepository {
    final private GameRoomRepositoryImpl impl;

    public void createGR(GameRoomEntity entity) {
        impl.save(entity);
    }

    public GameRoomEntity readGR(Long room_id) {
        return impl.findById(room_id).get();
    }

    public void updateGR(GameRoomEntity entity) {
        GameRoomEntity room = impl.findById(entity.getRoom_id()).get();
        // 변환내용 적용
        room.setBudget(entity.getBudget());
        room.setCurr_player(entity.getCurr_player());
        room.setTot_player(entity.getTot_player());
        impl.save(room);
    }

    public void deleteGR(Long room_id) {
        impl.deleteById(room_id);
    }
}
