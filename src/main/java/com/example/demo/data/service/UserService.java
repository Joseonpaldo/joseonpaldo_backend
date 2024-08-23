package com.example.demo.data.service;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.social.provider.JwtProvider;
import com.example.demo.social.requestReponseDto.SignInResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final JwtProvider jwtProvider;

    // USER
    // CREATE / check
    public Long loginUser(UserEntity entity) {
        String uii = entity.getUserIdentifyId();
        UserEntity user = userRepo.findByUserIdentifyId(uii);
        //고유 아이디로 로그인하지 않았다면
        if (user == null) {
            //신규 생성자는 db에 저장
            userRepo.createUser(entity);
        }else{
            entity.setUser_id(user.getUser_id());
        }
        return entity.getUser_id();
    }

    //재발급
    public ResponseEntity<?> refreshAccessToken(String refreshToken) {
        if (refreshToken == null || !jwtProvider.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }

        String userIdentifyId = jwtProvider.validate(refreshToken);
//        String newAccessToken = jwtProvider.createAccessToken(userIdentifyId);

        return null;
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

    public void updatePlayCount(Long user_id, String gameType) {
        if (gameType == "2p") {
            userRepo.updateUser2pTotCount(user_id);
        } else {
            userRepo.updateUser4pTotCount(user_id);
        }
    }

    public void updateWinCount(Long user_id, String gameType) {
        if (gameType == "2p") {
            userRepo.updateUser2pWinCount(user_id);
        } else {
            userRepo.updateUser4pWinCount(user_id);
        }
    }

    // Delete
    public void deleteUser(Long user_id) {
        userRepo.deleteUser(user_id);
    }
}
