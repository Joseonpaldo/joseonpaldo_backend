package com.example.demo.data.repository;

import com.example.demo.data.entity.FriendRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRelationRepositoryImpl extends JpaRepository<FriendRelationEntity, Long> {
    Long countByUserId1AndUserId2OrUserId2AndUserId1(Long userId1, Long userId2, Long userId3, Long userId4);

    Long countByUserId1OrUserId2AndUserId2OrUserId1(Long userId1, Long userId2, Long userId3, Long userId4);

    // 둘 다 jwt my id값
    List<FriendRelationEntity> findByUserId1OrUserId2(Long userId1, Long userId2);

    void deleteByUserId1AndUserId2OrUserId2AndUserId1(Long userId1, Long userId2, Long userId3, Long userId4);

    @Query("SELECT f FROM FriendRelationEntity f WHERE (f.userId1 = :userId1 AND f.userId2 = :userId2) OR (f.userId1 = :userId2 AND f.userId2 = :userId1)")
    Optional<FriendRelationEntity> findFriendRelation(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    Optional<FriendRelationEntity> findByUserId1AndUserId2OrUserId2AndUserId1(Long userId1, Long userId2, Long userId2Reversed, Long userId1Reversed);

}
