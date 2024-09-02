package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.entity.GameLogEntity;

public interface GameLogRepositoryImpl extends JpaRepository<GameLogEntity, Long> {

}
