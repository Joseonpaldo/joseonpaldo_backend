package com.example.demo.data.controller;

import com.example.demo.data.service.ChatRoomService;
import org.springframework.web.bind.annotation.*;

import com.example.demo.data.dto.UserPrintDto;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;
import com.example.demo.security.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final ChatRoomService chatRoomService;

    @GetMapping("/user")
    public UserEntity getUser(@RequestParam("user_id") Long user_id) {
        System.out.println("i am in here");
        return userService.getUser(user_id);
    }

    @GetMapping("/user/data")
    public ResponseEntity getUserData(HttpServletRequest request) {
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @GetMapping("/user/{jwt}")
    public UserPrintDto getUserByJWT(@PathVariable("jwt") String jwt) {
        Long userId = Long.parseLong(jwtProvider.getClaimsFromToken(jwt).get("user_id"));
        return userService.findUserPrintById(userId);
    }
}
