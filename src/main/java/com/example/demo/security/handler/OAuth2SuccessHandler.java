package com.example.demo.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.security.handler.user.CustomOAuth2User;
import com.example.demo.security.jwt.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        System.out.println(oauth2User.getAttributes());

        String accessToken = jwtProvider.createAccessToken(Long.parseLong("1"), "google");
        String refreshToken = jwtProvider.createRefreshToken(Long.parseLong("1"), "google");

        //access cookie
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setMaxAge(30 * 60); // 1 days
        accessTokenCookie.setPath("/");    //특정 경로에서 유효한지 확인하는 메소드
        response.addCookie(accessTokenCookie);

        // refresh cookie
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(1 * 24 * 60 * 60); // 1 days
        refreshTokenCookie.setPath("/");    //특정 경로에서 유효한지 확인하는 메소드
        response.addCookie(refreshTokenCookie);

        response.sendRedirect("/returnCookie");
    }
}
