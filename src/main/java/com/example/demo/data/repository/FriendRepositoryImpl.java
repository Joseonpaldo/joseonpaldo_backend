package com.example.demo.data.repository;

import com.example.demo.data.entity.FriendRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendRepositoryImpl extends JpaRepository <FriendRelationEntity,Long> {
    @Query(value = "select friend_id from friend_relation where id_1=:userId",
            nativeQuery = true)
    List<Long> findFriendListByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from friend_relation where (id_1 = :userId AND friend_id = :friendId) OR (id_1 = :friendId AND friend_id = :userId)",
            nativeQuery = true)
    Integer deleteFriend(@Param("userId") Long userId,@Param("friendId") Long friendId);
}
