package com.example.demo.data.contoller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.data.service.UserGameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserGameRestController {
    final private UserGameService userGameService;

    //UPDATE
    @PutMapping("/user/pcount")
    public void updatePlaycount(@RequestParam Long user_id, @RequestParam("playcount") int playcount) {

    }

    @PutMapping("/user/win")
    public void updateWin(@RequestParam Long user_id, @RequestParam("playcount") int playcount) {

    }
}
