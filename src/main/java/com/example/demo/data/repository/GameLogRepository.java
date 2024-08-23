package com.example.demo.data.repository;

import com.example.demo.data.repository.inter.GameLogRepositoryImpl;
import org.springframework.stereotype.Repository;

import com.example.demo.data.entity.GameLogEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GameLogRepository {
    final private GameLogRepositoryImpl impl;

    public void createGL(GameLogEntity entity) {
        impl.save(entity);
    }


    //채팅 삭제기능 보통 없음
//    public void deleteGL(Long log_id) {
//        impl.deleteById(log_id);
//    }
}