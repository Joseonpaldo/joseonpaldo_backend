package com.example.demo.data.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public UserEntity getUser(@RequestParam("user_id") Long user_id) {
        System.out.println("i am in here");
        return userService.getUser(user_id);
    }
}
