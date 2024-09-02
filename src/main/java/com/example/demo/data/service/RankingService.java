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
        return userRepositoryImpl.getTopThree2p();
    }
}
