package com.example.demo.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.data.dto.UserPrintDto;
import com.example.demo.data.entity.UserEntity;

public interface UserRepositoryImpl extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUserIdentifyId(String userIdentifyId);

    @Query("SELECT new com.example.demo.data.dto.UserPrintDto(u.user_id, u.email, u.nickname, u.socialProvider, u.profilePicture, u.tot_2p, u.win_2p, u.tot_4p, u.win_4p) " +
           "FROM UserEntity u WHERE u.user_id = :userId")
    Optional<UserPrintDto> findUserPrintById(Long userId);
}
