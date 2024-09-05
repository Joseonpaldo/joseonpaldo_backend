package com.example.demo.data.controller;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FriendController {
    private final UserService userService;

    @GetMapping("/user/Friend")
    public List<Long> getFriendList(@RequestParam Long userId){
        return userService.getFriendList(userId);
    }

    @PutMapping("/user/Friend")
    public void addFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        UserEntity user=userService.getUser(userId);
        UserEntity friend=userService.getUser(friendId);
        userService.addFriend(user,friend);
    }

    @DeleteMapping("/user/Friend")
    public void deleteFriend(@RequestParam Long userId,@RequestParam Long friendId){
        userService.deleteFriend(userId,friendId);
    }
}
