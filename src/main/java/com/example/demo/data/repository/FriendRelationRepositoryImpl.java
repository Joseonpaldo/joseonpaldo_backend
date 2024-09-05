package com.example.demo.data.repository;

import com.example.demo.data.entity.FriendRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRelationRepositoryImpl extends JpaRepository<FriendRelationEntity, Long> {
    Long countByUserId1OrUserId2AndUserId2OrUserId1(Long userId1, Long userId2, Long userId3, Long userId4);

    List<FriendRelationEntity> findByUserId1OrUserId2(Long userId1, Long userId2);
}
