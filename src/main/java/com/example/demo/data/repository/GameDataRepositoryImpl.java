package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.entity.GameDataEntity;
import org.springframework.data.jpa.repository.Query;

public interface GameDataRepositoryImpl extends JpaRepository<GameDataEntity, Long> {
    @Query("SELECT g.myTurn FROM GameDataEntity g WHERE g.gameRoom.roomId = :roomId and g.user.userId = :userId")
    Integer findOrderByRoomIdAndUserId(Long roomId , Long userId);
}
