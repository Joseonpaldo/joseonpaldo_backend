package com.example.demo.data.controller;

import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.FriendRelationRepositoryImpl;
import com.example.demo.data.repository.UserRepositoryImpl;
import com.example.demo.data.service.UserService;
import com.example.demo.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendRestController {
    private final FriendRelationRepositoryImpl friendRelationRepository;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @PostMapping("/check")
    public boolean listCheck(@RequestParam Long user_id, @RequestParam Long friend_id){
        //친구 목록에 있다면 false 리턴
        if(friendRelationRepository.countByUserId1OrUserId2AndUserId2OrUserId1(user_id, friend_id, friend_id, user_id) == 1){
            return false;
        }
        return true;
    }

    @PutMapping("/")
    public void addFriend(@RequestParam String jwt, @RequestParam Long friendId){
        Map<String,String> map=jwtProvider.getClaimsFromToken(jwt);
        Long userId=Long.parseLong(map.get("user_id"));
        userService.addFriend(userId,friendId);
    }

    @DeleteMapping("/")
    public void deleteFriend(@RequestParam String jwt, @RequestParam Long friendId){
        Map<String,String> map=jwtProvider.getClaimsFromToken(jwt);
        Long userId=Long.parseLong(map.get("user_id"));
        userService.deleteFriend(userId,friendId);
    }

    @GetMapping("/")
    public List<UserEntity> getList(@RequestParam String jwt){
        Map<String, String> map = jwtProvider.getClaimsFromToken(jwt);
        Long userId = Long.parseLong(map.get("user_id"));
        return userService.getFriendList(userId);
    }
}
