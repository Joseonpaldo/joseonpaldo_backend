package com.example.demo.data.repository;

import com.example.demo.data.entity.FriendLogEntity;
import com.example.demo.data.repository.FriendLogRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendLogRepository {
    private final FriendLogRepositoryImpl friendLogRepositoryImpl;

    public void insertLog(FriendLogEntity friendLogEntity) {friendLogRepositoryImpl.save(friendLogEntity);}

    public List<FriendLogEntity> findLogList(String from_id,String to_id) {return friendLogRepositoryImpl.findLogList(from_id,to_id);}


}
