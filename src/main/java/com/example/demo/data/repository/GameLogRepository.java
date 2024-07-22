package com.example.demo.data.repository;

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

    public GameLogEntity readGL(Long log_id) {
        return impl.findById(log_id).get();
    }

    public void deleteGL(Long log_id) {
        impl.deleteById(log_id);
    }
}
