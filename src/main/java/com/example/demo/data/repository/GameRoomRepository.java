package com.example.demo.data.repository;

import com.example.demo.data.repository.inter.GameRoomRepositoryImpl;
import org.springframework.stereotype.Repository;

import com.example.demo.data.entity.GameRoomEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GameRoomRepository {
    final private GameRoomRepositoryImpl impl;

    //CREATE
    public void createGR(GameRoomEntity entity) {
        impl.save(entity);
    }

    //READ
    public GameRoomEntity readGR(Long room_id) {
        return impl.findById(room_id).get();
    }

    //UPDATE
    public void updateGR(GameRoomEntity update) {
        impl.save(update);
    }

    public void updateGRManager(GameRoomEntity update) {
        impl.save(update);
    }

    //DELETE
    public void deleteGR(Long room_id) {
        impl.deleteById(room_id);
    }
}
