package com.example.demo.data.service;

import com.example.demo.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGameService {
    private final UserRepository userRepo;

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
}
