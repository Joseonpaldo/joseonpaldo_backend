package com.example.demo.data.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.service.RankingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;
}
