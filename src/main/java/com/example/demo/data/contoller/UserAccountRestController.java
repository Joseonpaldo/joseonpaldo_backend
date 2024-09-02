package com.example.demo.data.contoller;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;
import com.example.demo.security.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAccountRestController {
    final private UserService userService;
    final private JwtProvider jwtProvider;

    //CREATE : OAuth2UserInfoService -> userAccountService -> loginUser()

    //READ : Authentication
    @GetMapping("/auth")
    public UserEntity getUserData(HttpServletRequest request) {
        UserEntity userEntity;
        String authorizationHeader = request.getHeader("Authorization");


        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token=authorizationHeader.substring(7);

            Boolean user_confirm = jwtProvider.validateToken(token);
            Map<String, String> jwtMap=jwtProvider.getClaimsFromToken(token);
            String user_id=jwtMap.get("user_id");


            if(user_confirm == null) {
                //userservice에 refreshtoken쿠키에서 읽어서 검증후 재발급 하는거 구현해야함
                return null;
            }

            userEntity = userService.getUser(Long.valueOf(user_id));
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

        userService.deleteUser(user_id);
    }

    @PutMapping("/user/addFriend")
    public void addFriend(@RequestParam Long user_id, @RequestParam Long friend_id) {
        UserEntity myEntity = userService.getUser(user_id);
        UserEntity friendEntity = userService.getUser(friend_id);

        if (myEntity == null || friendEntity == null) {
            throw new IllegalArgumentException("유효하지 않은 사용자 ID입니다.");
        }

        String friendList = myEntity.getFriendList();

        // Null 또는 "null" 문자열 처리
        if (friendList == null || friendList.equals("null") || friendList.isEmpty()) {
            friendList = "";
        }

        // 내 친구 목록에 friend_id가 없으면 추가
        boolean myExists = Arrays.stream(friendList.split(","))
                .anyMatch(id -> id.equals(friend_id.toString()));

        if (!myExists) {
            if (!friendList.isEmpty()) {
                friendList += ",";
            }
            friendList += friend_id;
            myEntity.setFriendList(friendList);
            userService.updateFriendList(myEntity);
        }

        // 친구의 친구 목록에 user_id가 없으면 추가

        String friendListOfFriend = friendEntity.getFriendList();

        if (friendListOfFriend == null || friendListOfFriend.equals("null") || friendListOfFriend.isEmpty()) {
            friendListOfFriend = "";
        }

        boolean friendExists = Arrays.stream(friendListOfFriend.split(","))
                .anyMatch(id -> id.equals(user_id.toString()));

        if (!friendExists) {
            if (!friendListOfFriend.isEmpty()) {
                friendListOfFriend += ",";
            }
            friendListOfFriend += user_id;
            friendEntity.setFriendList(friendListOfFriend);
            userService.updateFriendList(friendEntity);
        }
    }


    @GetMapping("/user/deleteFriend")
    public void deleteFriend(@RequestParam Long user_id,@RequestParam Long friend_id){
        UserEntity myEntity=userService.getUser(user_id);
        UserEntity friendEntity=userService.getUser(friend_id);

        String myList=myEntity.getFriendList();
        String friendList=friendEntity.getFriendList();

        String updatedMyList = Arrays.stream(myList.split(","))
                .filter(id -> !id.equals(friend_id.toString()))
                .collect(Collectors.joining(","));
        myEntity.setFriendList(updatedMyList);

        String updatedFriendList=Arrays.stream(friendList.split(",")).filter(id->!id.equals(user_id.toString()))
                .collect(Collectors.joining(","));
        friendEntity.setFriendList(updatedFriendList);

        userService.updateFriendList(myEntity);
        userService.updateFriendList(friendEntity);
    }
}
