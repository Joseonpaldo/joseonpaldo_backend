package com.example.demo.data.repository.inter;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.entity.UserEntity;

public interface UserRepositoryImpl extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    long countByEmailAndSocialProvider(String email, String socialProvider);

    UserEntity findByUserIdentifyId(String identifyId);
}
