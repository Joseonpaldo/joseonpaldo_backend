package com.example.demo.data.service;

import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.repository.FriendRepositoryImpl;
import org.springframework.stereotype.Service;

import com.example.demo.data.dto.UserPrintDto;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepositoryImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryImpl userRepositoryImpl;
    private final FriendRepositoryImpl friendRepositoryImpl;

    public UserEntity getUser(Long userId) {
        UserEntity user;
        try {
            user = userRepositoryImpl.findById(userId).get();
        } catch (Exception e) {
            return new UserEntity();
        }
        return user;
    }

    public UserEntity getUser(String userIdentifyId) {
        return userRepositoryImpl.findByUserIdentifyId(userIdentifyId);
    }

    public void save(UserEntity user) {
        userRepositoryImpl.save(user);
    }

    public UserPrintDto findUserPrintById(Long userId) {
        return userRepositoryImpl.findUserPrintById(userId).get();
    }

    public void removeProviderAccessToken(Long userId) {
        UserEntity user = userRepositoryImpl.findById(userId).get();
        user.setProviderAccessToken(null);
        userRepositoryImpl.save(user);
    }

    @Transactional
    public void addFriend(UserEntity userId, UserEntity friendId) {
        // 두 사용자의 친구 관계를 가져옴
        FriendRelationEntity userRelation = friendRepositoryImpl.findByUserId(userId.getUserId());
        FriendRelationEntity friendRelation = friendRepositoryImpl.findByUserId(friendId.getUserId());

        // user의 친구 관계 설정
        if (userRelation == null) {
            userRelation = new FriendRelationEntity();
            userRelation.setUserId(userId);
            userRelation.setFriendList(new ArrayList<>());
        }
        userRelation.getFriendList().add(friendId.getUserId());

        // friend의 친구 관계 설정
        if (friendRelation == null) {
            friendRelation = new FriendRelationEntity();
            friendRelation.setUserId(friendId);
            friendRelation.setFriendList(new ArrayList<>());
        }
        friendRelation.getFriendList().add(userId.getUserId());

        // 두 엔티티를 저장
        friendRepositoryImpl.save(userRelation);
        friendRepositoryImpl.save(friendRelation);
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        UserEntity user = userRepositoryImpl.findById(userId).get();
        UserEntity friend = userRepositoryImpl.findById(friendId).get();

        FriendRelationEntity myEntity = friendRepositoryImpl.findByUserId(userId);
        FriendRelationEntity reverseEntity = friendRepositoryImpl.findByUserId(friendId);

        List<Long> myList = myEntity.getFriendList();
        myList.remove(friend.getUserId());
        myEntity.setFriendList(myList);

        List<Long> reverseList = reverseEntity.getFriendList();
        reverseList.remove(user.getUserId());
        reverseEntity.setFriendList(reverseList);

        friendRepositoryImpl.save(myEntity);
        friendRepositoryImpl.save(reverseEntity);
    }

    public List<Long> getFriendList(Long userId) {
        return friendRepositoryImpl.findFriendListByUserId(userId);
    }
}
