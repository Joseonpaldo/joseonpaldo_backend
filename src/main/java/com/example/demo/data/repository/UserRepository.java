package com.example.demo.data.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.data.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    final private UserRepositoryImpl impl;

    public void createUser(UserEntity entity) {
        impl.save(entity);
    }

    public UserEntity readUser(Long user_id) {
        return impl.findById(user_id).get();
    }

    public void updateUser(UserEntity entity) {
        UserEntity update = impl.findById(entity.getUser_id()).get();
        // Update 할 내역들 update처리

        impl.save(update);
    }

    public void deleteUser(Long user_id) {
        impl.deleteById(user_id);
    }
}
