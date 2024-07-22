package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.data.entity.GameDataEntity;
import com.example.demo.data.entity.GameLogEntity;
import com.example.demo.data.entity.GameRoomEntity;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.GameDataRepository;
import com.example.demo.data.repository.GameEstateRepository;
import com.example.demo.data.repository.GameLogRepository;
import com.example.demo.data.repository.GameRoomRepository;
import com.example.demo.data.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//환용테스트
//환용테스트2

@Service
@RequiredArgsConstructor
public class MainService {
    final private UserRepository userRepo;
    final private GameRoomRepository grRepo;
    final private GameDataRepository gdRepo;
    final private GameLogRepository glRepo;
    final private GameEstateRepository geRepo;

    // USER
    // CREATE
    public void createUser(UserEntity entity) {
        userRepo.createUser(entity);
    }

    // READ
    public UserEntity readUser(Long user_id) {
        return userRepo.readUser(user_id);
    }

    // UPDATE
    public void updateUser(UserEntity entity) {
        userRepo.updateUser(entity);
    }

    // Delete
    public void deleteUser(Long user_id) {
        userRepo.deleteUser(user_id);
    }

    // GameRoom
    // CREATE
    public void createGR(GameRoomEntity entity) {
        grRepo.createGR(entity);
    }

    // READ
    public GameRoomEntity readGR(Long room_id) {
        return grRepo.readGR(room_id);
    }

    // UPDATE
    public void updateGR(GameRoomEntity entity) {
        grRepo.updateGR(entity);
    }

    // DELETE
    public void deleteGR(Long room_id) {
        grRepo.deleteGR(room_id);
    }

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

    // GameLog
    // CREATE
    public void createGL(GameLogEntity entity) {
        glRepo.createGL(entity);
    }

    // READ
    public GameLogEntity readGL(Long log_id) {
        return glRepo.readGL(log_id);
    }

    // DELETE
    public void deleteGL(Long log_id) {
        glRepo.deleteGL(log_id);
    }

    // GameEstate
    // READ
    public void readGE(Long estate_id) {
        geRepo.readGE(estate_id);
    }
}
