package com.example.demo.data.repository.inter;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.entity.GameEstateEntity;

public interface GameEstateRepositoryImpl extends JpaRepository<GameEstateEntity, Long> {

}
