package com.example.demo.data.repository.inter;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.entity.GameRoomEntity;

public interface GameRoomRepositoryImpl extends JpaRepository<GameRoomEntity, Long> {
}
