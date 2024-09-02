package com.example.demo.data.contoller;

import com.example.demo.data.entity.FriendRelationEntity;
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
    public List<Long> getFriendList(@RequestParam Long user_id){
        return userService.getFriendList(user_id);
    }

    @PutMapping("/user/Friend")
    public void addFriend(@RequestParam Long user_id, @RequestParam Long friend_id) {
        UserEntity user=userService.getUser(user_id);
        UserEntity friend=userService.getUser(friend_id);
        userService.addFriend(user,friend);
    }


    @DeleteMapping("/user/Friend")
    public void deleteFriend(@RequestParam Long user_id,@RequestParam Long friend_id){
        userService.deleteFriend(user_id,friend_id);
    }
}
