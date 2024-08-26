package com.example.demo.data.contoller;

import com.example.demo.social.filer.JwtAuthenticationFilter;
import com.example.demo.social.provider.JwtProvider;
import com.example.demo.social.requestReponseDto.SignInResponseDto;
import com.nimbusds.jwt.JWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {
    final private UserService userService;
    final private JwtProvider jwtProvider;
    final private JwtAuthenticationFilter jwtAuthenticationFilter;

    @GetMapping("/health")
    public String healthCheck() {
         return "ok";
    }



    // USER
    @GetMapping("/user")
    public UserEntity getUser(@RequestParam("user_id") Long user_id) {
        return userService.readUser(user_id);
    }

    @GetMapping("/user/data")
    public ResponseEntity getUserData(HttpServletRequest request) {
        System.out.println(request.getHeader("Authorization"));
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @GetMapping("/user/{jwt}")
    public UserEntity getUserByJWT(@PathVariable("jwt") String jwt) {
        var userId = jwtProvider.getUserIdByJWT(jwt);
        return userService.readUser(Long.valueOf(userId));
    }

    @PostMapping("/user/auth")
    public void postUser(@RequestBody UserEntity entity, @RequestParam String social_provider) {

    }

//    //refreshtoken 검사 후 accesstoken재발급
//    @PostMapping("/user/auth/reissue")
//    public String refreshAccessToken(@RequestParam String old_token) {
//        jwtProvider.validate(old_token);
//        return jwtProvider.createAccessToken(user_id);
//    }



//    //UPDATE
//    @PutMapping("/user/{id}")
//    public void updateNickname(@PathVariable("id") Long user_id, @RequestParam("nickname") String nickname) {
//        userService.updateUserNickname(user_id, nickname);
//    }
//
//    @PutMapping("/user/pcount")
//    public void updatePlaycount(@RequestParam Long user_id, @RequestParam("playcount") int playcount) {
//        userService.updatePlayCount(user_id, playcount);
//    }
//
//    @PutMapping("/user/win")
//    public void updateWin(@RequestParam Long user_id, @RequestParam("playcount") int playcount) {
//        userService.updateWinCount(user_id, playcount);
//    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long user_id) {
        userService.deleteUser(user_id);
    }
}
