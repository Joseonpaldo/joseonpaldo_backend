package com.example.demo.data.service;

import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.repository.FriendRelationRepositoryImpl;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.example.demo.data.dto.UserPrintDto;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepositoryImpl;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryImpl userRepositoryImpl;
    private final FriendRelationRepositoryImpl friendImpl;
    private final UserRepositoryImpl userRepository;

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
    public void deleteFriend(Long userId, Long friendId) {
        friendImpl.deleteByUserId1OrUserId2AndUserId2OrUserId1(userId,friendId,friendId,userId);
    }

    public List<UserPrintDto> getFriendList(Long userId) {
        List<FriendRelationEntity> relationList = friendImpl.findByUserId1OrUserId2(userId, userId);
        //열에서 내 값 지우고 친구 id만 뽑아 리스트 저장
        List<Long> extractedFriendsId = relationList.stream()
                .flatMap(relation -> {
                    if (relation.getUserId1().equals(userId)) {
                        return Stream.of(relation.getUserId2());
                    } else if (relation.getUserId2().equals(userId)) {
                        return Stream.of(relation.getUserId1());
                    }
                    return Stream.empty();
                })
                .toList();

        List<UserPrintDto> friendList = new ArrayList<>();

        //뽑아낸 id로 userEntity 리스트 만들어 저장
        for(Long id : extractedFriendsId){
            UserEntity user = userRepositoryImpl.findById(id).get();
            UserPrintDto userPrintDto=UserPrintDto.builder().
                    userId(user.getUserId()).
                    email(user.getEmail()).
                    nickname(user.getNickname()).
                    tot_4p(user.getTot4p()).
                    tot_2p(user.getTot2p()).
                    win_4p(user.getWin4p()).
                    win_2p(user.getWin2p()).
                    socialProvider(user.getSocialProvider()).
                    profilePicture(user.getProfilePicture()).
                    build();
            friendList.add(userPrintDto);
        }

        return friendList;
    }

}
