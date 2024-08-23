package com.example.demo.data.service;

import com.example.demo.data.entity.GameDataEntity;
import com.example.demo.data.repository.GameDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameDataService {
    final private GameDataRepository gdRepo;

    // GameData
    // CREATE
    public void createGD(GameDataEntity entity) {
        gdRepo.createGD(entity);
    }

    // READ
    public GameDataEntity readGD(Long data_id) {
        return gdRepo.readGD(data_id);
    }

    // UPDATE
    public void updateGD(GameDataEntity entity) {
        gdRepo.updateGD(entity);
    }

    // DELETE
    public void deleteGD(Long data_id) {
        gdRepo.deleteGD(data_id);
    }
}
