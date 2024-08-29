package com.example.demo.security.handler.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomOAuth2User {
    private Long userId;
    private String provider;

    public Long getUserId() {
        return userId;
    }

    public String getProvider() {
        return provider;
    }
}
