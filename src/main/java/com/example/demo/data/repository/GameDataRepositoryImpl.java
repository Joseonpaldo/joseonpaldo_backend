package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.entity.GameDataEntity;

public interface GameDataRepositoryImpl extends JpaRepository<GameDataEntity, Long> {

}
