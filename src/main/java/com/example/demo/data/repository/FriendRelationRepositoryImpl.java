package com.example.demo.data.repository;

import com.example.demo.data.entity.FriendRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FriendRelationRepositoryImpl extends JpaRepository<FriendRelationEntity, Long> {
    Optional <FriendRelationEntity> findByUserId1AndUserId2OrUserId2AndUserId1(Long userId1,Long userId2,Long userId3, Long userId4);

    Long countByUserId1OrUserId2AndUserId2OrUserId1(Long userId1, Long userId2, Long userId3, Long userId4);

    List<FriendRelationEntity> findByUserId1OrUserId2(Long userId1, Long userId2);

    void deleteByUserId1OrUserId2AndUserId2OrUserId1(Long userId1, Long userId2, Long userId3, Long userId4);
}
