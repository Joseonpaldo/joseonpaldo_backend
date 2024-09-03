package com.example.demo.data.controller;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserAccountService;
import com.example.demo.security.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAccountRestController {
    final private UserAccountService userService;
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
}
