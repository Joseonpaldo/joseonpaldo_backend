package com.example.demo.data.repository;

import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendRepositoryImpl extends JpaRepository <FriendRelationEntity,Long> {
    @Query(value = "select friend_list from friend_relation where user_id=:userId",
            nativeQuery = true)
    List<Long> findFriendListByUserId(@Param("userId") Long userId);

    @Query(value = "select * from friend_relation where user_id=:userId",nativeQuery = true)
    FriendRelationEntity findByUserId(@Param("userId") Long userId);
}
