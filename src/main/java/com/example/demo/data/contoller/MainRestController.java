package com.example.demo.data.contoller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.service.MainService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class MainRestController {
    final private MainService service;

    @GetMapping("/user")
    public UserEntity getUser(@RequestParam("user_id") Long user_id) {
        return service.readUser(user_id);
    }

    @PostMapping("/user")
    public void postUser(@RequestBody UserEntity entity) {
        service.createUser(entity);
    }

    @PutMapping("/user/{id}")
    public void putUser(@PathVariable("id") Long user_id, @RequestBody UserEntity entity) {
        entity.setUser_id(user_id);
        service.updateUser(entity);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long user_id) {
        service.deleteUser(user_id);
    }
}
