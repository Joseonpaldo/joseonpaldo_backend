package com.example.demo.data.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.data.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    final private UserRepositoryImpl impl;

    // Create
    public void createUser(UserEntity entity) {
        impl.save(entity);
    }

    // Read
    public UserEntity readUser(Long user_id) {
        return impl.findById(user_id).get();
    }

    // Update
    public void updateUserNickname(UserEntity entity) {
        UserEntity update = impl.findById(entity.getUser_id()).get();
        // Update 할 내역들 update처리
        update.setNickname(entity.getNickname());
        impl.save(update);
    }

    public void updateUser2pWinCount(Long user_id) {
        UserEntity update = impl.findById(user_id).get();
        update.setWin_2p(update.getWin_2p() + 1);
        impl.save(update);
    }

    public void updateUser2pTotCount(Long user_id) {
        UserEntity update = impl.findById(user_id).get();
        update.setTot_2p(update.getTot_2p() + 1);
        impl.save(update);
    }

    public void updateUser4pWinCount(Long user_id) {
        UserEntity update = impl.findById(user_id).get();
        update.setWin_4p(update.getWin_4p() + 1);
        impl.save(update);
    }

    public void updateUser4pTotCount(Long user_id) {
        UserEntity update = impl.findById(user_id).get();
        update.setTot_4p(update.getTot_4p() + 1);
        impl.save(update);
    }

    // Delete
    public void deleteUser(Long user_id) {
        impl.deleteById(user_id);
    }
}
