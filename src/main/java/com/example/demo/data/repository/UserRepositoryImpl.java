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

    UserEntity findByuserId(Long user_id);

    // Ranking Stuff
    public List<UserEntity> findTop3ByOrderByWinRate2pDesc();
    public List<UserEntity> findTop10ByOrderByWinRate2pDesc();
    public List<UserEntity> findTop3ByOrderByWinRate4pDesc();
    public List<UserEntity> findTop10ByOrderByWinRate4pDesc();
}
