package com.example.demo.data.service;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.data.repository.inter.UserRepositoryImpl;
import com.example.demo.social.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserRepository userRepo;
    private final JwtProvider jwtProvider;
    private final UserRepositoryImpl userRepositoryImpl;

    // USER
    // CREATE or check
    public Long loginUser(UserEntity entity) {
        String uii = entity.getUserIdentifyId();
        UserEntity user = userRepo.findByUserIdentifyId(uii);
        //고유 아이디로 로그인하지 않았다면
        if (user == null) {
            //신규 생성자는 db에 저장
            userRepo.createUser(entity);
        }else{
            entity.setUser_id(user.getUser_id());
            userRepo.createUser(entity);
        }
        return entity.getUser_id();
    }

    // READ
    public UserEntity readUser(Long user_id) {
        return userRepo.readUser(user_id);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }


    // UPDATE
    public void updateUserNickname(UserEntity entity) {
        userRepo.updateUserNickname(entity);
    }

    public void updateFriendList(UserEntity entity){userRepo.createUser(entity);}


    // Delete
    public void deleteUser(Long user_id) {
        userRepo.deleteUser(user_id);
    }
}
