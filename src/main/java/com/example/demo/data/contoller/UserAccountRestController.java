package com.example.demo.data.contoller;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserAccountService;
import com.example.demo.data.service.UserGameService;
import com.example.demo.social.filer.JwtAuthenticationFilter;
import com.example.demo.social.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAccountRestController {
    final private UserAccountService userAccountService;
    final private JwtProvider jwtProvider;

    //CREATE : OAuth2UserInfoService -> userAccountService -> loginUser()

    //READ : Authentication
    @GetMapping("/auth")
    public UserEntity getUserData(HttpServletRequest request) {
        UserEntity userEntity;
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token=authorizationHeader.substring(7);

            Long user_id = jwtProvider.validate(token);

            if(user_id == null) {
                //userservice에 refreshtoken쿠키에서 읽어서 검증후 재발급 하는거 구현해야함
                return null;
            }

            userEntity = userAccountService.readUser(user_id);
            userEntity.setUserIdentifyId(null);
            userEntity.setProviderAccessToken(null);

            return userEntity;
        } else {
            throw new RuntimeException("Authorization header is missing or invalid.");
        }
    }

    //UPDATE

    //DELETE
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long user_id) {

        userAccountService.deleteUser(user_id);
    }

    @PutMapping("/user/addFriend")
    public void addFriend(@RequestParam Long user_id,@RequestParam Long friend_id) {
        UserEntity myEntity=userAccountService.readUser(user_id);
        String friendList=myEntity.getFriendList();
        if(friendList.equals("null")){
            friendList="";
        }

        boolean myExists = Arrays.stream(friendList.split(","))
                .anyMatch(name -> name.equals(friend_id.toString()));
        if(!myExists){
            myEntity.setFriendList(friendList+friend_id+",");
            userAccountService.updateFriendList(myEntity);
        }


        UserEntity friendEntity=userAccountService.readUser(friend_id);
        String friendListOfFriend=friendEntity.getFriendList();
        if(friendListOfFriend.equals("null")){
            friendListOfFriend="";
        }

        boolean friendExists=Arrays.stream(friendListOfFriend.split(",")).anyMatch(name -> name.equals(user_id.toString()));
        if(!friendExists) {
            friendEntity.setFriendList(friendListOfFriend + user_id + ",");
            userAccountService.updateFriendList(friendEntity);
        }
    }
}
