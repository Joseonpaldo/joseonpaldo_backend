package com.example.demo.data.service;

import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.repository.FriendRepositoryImpl;
import org.springframework.stereotype.Service;

import com.example.demo.data.dto.UserPrintDto;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepositoryImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryImpl userRepositoryImpl;
    private final FriendRepositoryImpl friendRepositoryImpl;

    public UserEntity getUser(Long user_id) {
        UserEntity user;
        try {
            user = userRepositoryImpl.findById(user_id).get();
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
    public void addFriend(UserEntity user,UserEntity friend){
        List<Long> myList=friendRepositoryImpl.findFriendListByUserId(user);
        myList.add(friend.getUserId());

        FriendRelationEntity friendRelationEntity= FriendRelationEntity.builder().
                user(user).
                friendList(myList).
                build();
        List<Long> reverseList = friendRepositoryImpl.findFriendListByUserId(friend);
        reverseList.add(user.getUserId());

        FriendRelationEntity reverseEntity = FriendRelationEntity.builder().
                user(friend).
                friendList(reverseList).
                build();

        friendRepositoryImpl.save(friendRelationEntity);
        friendRepositoryImpl.save(reverseEntity);
    }

    @Transactional
    public void deleteFriend(Long user_id, Long friend_id){
        UserEntity user = userRepositoryImpl.findById(user_id).get();
        UserEntity friend = userRepositoryImpl.findById(friend_id).get();

        FriendRelationEntity myEntity=friendRepositoryImpl.findByUserId(user);
        FriendRelationEntity reverseEntity=friendRepositoryImpl.findByUserId(friend);

        List<Long> myList=myEntity.getFriendList();
        myList.remove(friend.getUserId());
        myEntity.setFriendList(myList);

        List<Long> reverseList = reverseEntity.getFriendList();
        reverseList.remove(user.getUserId());
        reverseEntity.setFriendList(reverseList);

        friendRepositoryImpl.save(myEntity);
        friendRepositoryImpl.save(reverseEntity);
    }

    public List<Long> getFriendList(Long userId){
        UserEntity entity=userRepositoryImpl.findById(userId).get();
        return friendRepositoryImpl.findFriendListByUserId(entity);
    }
}
