package com.example.demo.data.service;

import com.example.demo.data.entity.GameLogEntity;
import com.example.demo.data.repository.GameLogRepository;
import com.example.demo.data.repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameLogService {
    final private GameLogRepository glRepo;

    // GameLog
    // CREATE
    public void createGL(GameLogEntity entity) {
        glRepo.createGL(entity);
    }


    // DELETE
//    public void deleteGL(Long log_id) {
//        glRepo.deleteGL(log_id);
//    }
}
