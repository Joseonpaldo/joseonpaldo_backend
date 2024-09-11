package com.example.demo.data.service;

import com.example.demo.data.entity.ChatRoomEntity;
import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.repository.ChatMessageRepository;
import com.example.demo.data.repository.ChatRoomRepository;
import com.example.demo.data.repository.FriendRelationRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
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
    private final ChatRoomRepository chatRoomRepository;
    private final FriendRelationRepositoryImpl friendRelationRepositoryImpl;
    private final ChatMessageRepository chatMessageRepository;

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

    // UserService.java
    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        //친구관계 조회
        Optional<FriendRelationEntity> optionalFriendRelation = friendRelationRepositoryImpl.findFriendRelation(userId, friendId);

        if (optionalFriendRelation.isEmpty()) {
            throw new EntityNotFoundException("해당 친구 관계를 찾을 수 없습니다.");
        }

        FriendRelationEntity friendRelation = optionalFriendRelation.get();

        //해당 친구관계로 연결된 채팅방 조회
        Optional<ChatRoomEntity> optionalChatRoom = chatRoomRepository.findByFriendRelation(friendRelation);

        if (optionalChatRoom.isPresent()) {
            ChatRoomEntity chatRoom = optionalChatRoom.get();

            //채팅방의 모든 메시지 삭제
            chatMessageRepository.deleteByChatRoom(chatRoom);

            //채팅방 삭제
            chatRoomRepository.delete(chatRoom);
        }

        //친구관계 삭제
        friendRelationRepositoryImpl.delete(friendRelation);
    }



}
