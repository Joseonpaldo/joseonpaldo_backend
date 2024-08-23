package com.example.demo.data.repository;

import com.example.demo.data.repository.inter.GameEstateRepositoryImpl;
import org.springframework.stereotype.Repository;

import com.example.demo.data.entity.GameEstateEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GameEstateRepository {
    final private GameEstateRepositoryImpl impl;

    public GameEstateEntity readGE(Long estate_id) {
        return impl.findById(estate_id).get();
    }
}
