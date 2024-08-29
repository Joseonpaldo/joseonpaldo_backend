package com.example.demo.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;
import com.example.demo.security.filter.tools.HttpClientTools;
import com.example.demo.security.jwt.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final JwtProvider jwtProvider;
    private final HttpClientTools httpClientTools;
    private final UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        Long userId = Long.parseLong(jwtProvider.getClaimsFromToken(refreshToken).get("userId"));
        UserEntity user = userService.getUser(userId);

        httpClientTools.sendRequest(user.getSocialProvider(), user.getProviderAccessToken());
        userService.removeProviderAccessToken(userId);

        Cookie remove = new Cookie("refreshToken", null);
        remove.setPath("/");
        remove.setMaxAge(0);
        response.addCookie(remove);
    }

}
