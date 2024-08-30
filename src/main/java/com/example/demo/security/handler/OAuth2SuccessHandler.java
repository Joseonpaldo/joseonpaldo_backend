package com.example.demo.security.handler;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;
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
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {        
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println(oauth2User.getAttributes());

        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        String provider = oauth2Token.getAuthorizedClientRegistrationId();

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
            oauth2Token.getAuthorizedClientRegistrationId(),
            oauth2Token.getName()
        );
        String socialAccessToken = authorizedClient.getAccessToken().getTokenValue();
        UserEntity user;
        Long userId = null;

        // DB Save Process
        if(provider.equals("google")) {
            if(userService.getUser(oauth2User.getAttribute("sub").toString()) != null) {
                user = userService.getUser(oauth2User.getAttribute("sub").toString());
                user.setProviderAccessToken(socialAccessToken);
                userService.save(user);
                userId = user.getUser_id();
            } else {
                user = UserEntity.builder()
                    .email(oauth2User.getAttribute("email"))
                    .nickname(oauth2User.getAttribute("name"))
                    .socialProvider(provider)
                    .providerAccessToken(socialAccessToken)
                    .userIdentifyId(oauth2User.getAttribute("sub"))
                    .profilePicture(oauth2User.getAttribute("picture"))
                    .build();
                userService.save(user);
                userId = user.getUser_id();
            }
        }else if(provider.equals("kakao")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) oauth2User.getAttribute("kakao_account");
            Map<String, String> kakaoProfile = (Map<String, String>) kakaoAccount.get("profile");

            if(userService.getUser(oauth2User.getAttribute("id").toString()) != null) {
                user = userService.getUser(oauth2User.getAttribute("id").toString());
                user.setProviderAccessToken(socialAccessToken);
                userService.save(user);
                userId = user.getUser_id();
            }else {
                user = UserEntity.builder()
                    .email(kakaoAccount.get("email").toString())
                    .nickname(kakaoProfile.get("nickname"))
                    .socialProvider(provider)
                    .providerAccessToken(socialAccessToken)
                    .userIdentifyId(oauth2User.getAttribute("id").toString())
                    .profilePicture(kakaoProfile.get("profile_image_url"))
                    .build();
                userService.save(user);
                userId = user.getUser_id();
            }
        }else if(provider.equals("naver")) {
            Map<String, String> naverProfile = (Map<String, String>) oauth2User.getAttribute("response");
            
            if(userService.getUser(naverProfile.get("id")) != null) {
                user = userService.getUser(naverProfile.get("id"));
                user.setProviderAccessToken(socialAccessToken);
                userService.save(user);
                userId = user.getUser_id();
            }else {
                user = UserEntity.builder()
                    .email(naverProfile.get("email"))
                    .nickname(naverProfile.get("name"))
                    .socialProvider(provider)
                    .providerAccessToken(socialAccessToken)
                    .userIdentifyId(naverProfile.get("id"))
                    .profilePicture(naverProfile.get("profile_image"))
                    .build();
                userService.save(user);
                userId = user.getUser_id();
            }
        }

        String accessToken = jwtProvider.createAccessToken(userId, provider);
        String refreshToken = jwtProvider.createRefreshToken(userId, provider);

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