package com.example.demo.social.requestReponseDto;

import lombok.Getter;

@Getter
public class SignInResponseDto{
    private String accessToken;
    private int accessExpirationTime;

    public SignInResponseDto(String accessToken) {
        this.accessToken = accessToken;
        this.accessExpirationTime = 3600;
    }
}
