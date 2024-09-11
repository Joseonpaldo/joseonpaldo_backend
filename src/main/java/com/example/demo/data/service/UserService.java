package com.example.demo.data.service;

import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.repository.FriendRelationRepositoryImpl;
import org.springframework.stereotype.Service;

import com.example.demo.data.dto.UserPrintDto;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepositoryImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryImpl userRepositoryImpl;
    private final FriendRelationRepositoryImpl friendImpl;

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
    public void addFriend(Long userId, Long friendId) {
        FriendRelationEntity userRelation = FriendRelationEntity.builder().
                userId1(userId).userId2(friendId).build();

        friendImpl.save(userRelation);
    }

    @Transactional
    public void deleteFriend(Long userId1, Long userId2) {
        Optional<FriendRelationEntity> relation = friendImpl
                .findByUserId1AndUserId2(userId1, userId2);

        if (!relation.isPresent()) {
            relation = friendImpl
                    .findByUserId1AndUserId2(userId2, userId1);
        }

        relation.ifPresent(friendImpl::delete);
    }
}
