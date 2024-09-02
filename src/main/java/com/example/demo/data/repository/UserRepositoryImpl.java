package com.example.demo.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.data.dto.UserPrintDto;
import com.example.demo.data.entity.UserEntity;

public interface UserRepositoryImpl extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUserIdentifyId(String userIdentifyId);

    @Query("SELECT new com.example.demo.data.dto.UserPrintDto(u.userId, u.email, u.nickname, u.socialProvider, u.profilePicture, u.tot2p, u.win2p, u.tot4p, u.win4p) " +
           "FROM UserEntity u WHERE u.userId = :userId")
    Optional<UserPrintDto> findUserPrintById(Long userId);

    @Query("SELECT u FROM UserEntity u ORDER BY u.winRate2p DESC")
    public List<UserEntity> getTopThree2p();
}
