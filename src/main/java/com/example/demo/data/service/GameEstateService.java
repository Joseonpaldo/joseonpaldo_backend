package com.example.demo.data.service;

import com.example.demo.data.repository.GameEstateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameEstateService {
    final private GameEstateRepository geRepo;

    // GameEstate
    // READ
    public void readGE(Long estate_id) {
        geRepo.readGE(estate_id);
    }
}
