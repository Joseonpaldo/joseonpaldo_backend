package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.entity.UserEntity;

public interface UserRepositoryImpl extends JpaRepository<UserEntity, Long> {

}
