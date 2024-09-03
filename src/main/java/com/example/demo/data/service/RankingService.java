package com.example.demo.data.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepositoryImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final UserRepositoryImpl userRepositoryImpl;

    public List<UserEntity> getTopThree2p() {
        return userRepositoryImpl.findTop3ByOrderByWinRate2pDesc();
    }

    public List<UserEntity> getFourToTen2p() {
        return userRepositoryImpl.findTop10ByOrderByWinRate2pDesc().subList(3, 10);
    }

    public List<UserEntity> getTopThree4p() {
        return userRepositoryImpl.findTop3ByOrderByWinRate4pDesc();
    }

    public List<UserEntity> getFourToTen4p() {
        return userRepositoryImpl.findTop10ByOrderByWinRate4pDesc().subList(3, 10);
    }
}
