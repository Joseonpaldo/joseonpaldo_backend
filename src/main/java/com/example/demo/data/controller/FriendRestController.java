package com.example.demo.data.controller;

import com.example.demo.data.dto.UserPrintDto;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.FriendRelationRepositoryImpl;
import com.example.demo.data.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendRestController {
    private final FriendRelationRepositoryImpl friendRelationRepository;
    private final UserService userService;

    @PostMapping("/check")
    public boolean listCheck(@RequestParam Long user_id, @RequestParam Long friend_id){
        //친구 목록에 있다면 false 리턴
        if(friendRelationRepository.countByUserId1OrUserId2AndUserId2OrUserId1(user_id, friend_id, friend_id, user_id) == 1){
            return false;
        }
        return true;
    }

    @PutMapping("")
    public void addFriend(@RequestParam Long userId, @RequestParam Long friendId){
        userService.addFriend(userId,friendId);
    }

    @DeleteMapping("")
    public void deleteFriend(@RequestParam Long userId, @RequestParam Long friendId){
        userService.deleteFriend(userId,friendId);
    }

    @GetMapping("/list/{userId}")
    public List<UserPrintDto> getList(@RequestParam Long userId){
        System.out.println("dd" + userService.getFriendList(userId));
        return userService.getFriendList(userId);
    }
}