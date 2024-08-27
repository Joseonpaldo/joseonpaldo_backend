package com.example.demo.data.contoller;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserAccountService;
import com.example.demo.data.service.UserGameService;
import com.example.demo.social.filer.JwtAuthenticationFilter;
import com.example.demo.social.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        String token = request.getHeader("Authorization").substring(7);

        Long user_id = jwtProvider.validate(token);

        if(user_id == null) {
            //userservice에 refreshtoken쿠키에서 읽어서 검증후 재발급 하는거 구현해야함
            return null;
        }

        userEntity = userAccountService.readUser(user_id);
        userEntity.setUserIdentifyId(null);
        userEntity.setProviderAccessToken(null);

        return userEntity;
    }

    //UPDATE

    //DELETE
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long user_id) {
        userAccountService.deleteUser(user_id);
    }
}
