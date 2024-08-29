package com.example.demo.data.repository.inter;

import com.example.demo.data.entity.FriendLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendLogRepositoryImpl extends JpaRepository<FriendLogEntity,Long> {
    @Query(value = "SELECT * FROM friend_log WHERE (from_id = :id1 AND to_id = :id2) OR (from_id = :id2 AND to_id = :id1) ORDER BY msg_time desc", nativeQuery = true)
    List<FriendLogEntity> findLogList(@Param("id1") String from_id, @Param("id2") String to_id);

}
