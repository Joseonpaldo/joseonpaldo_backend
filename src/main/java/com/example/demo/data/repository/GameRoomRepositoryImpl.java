package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.entity.GameRoomEntity;

import java.util.List;

public interface GameRoomRepositoryImpl extends JpaRepository<GameRoomEntity, Long> {
    List<GameRoomEntity> findAllByRoomStatus(Integer roomStatus);

    // roomId로 GameRoomEntity를 가져오는 메소드
    GameRoomEntity findByRoomId(Long roomId);

}
