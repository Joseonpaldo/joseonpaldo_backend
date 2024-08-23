package com.example.demo.social.handler;

import com.example.demo.social.OAuth2.CustomOAuth2User;
import com.example.demo.social.provider.JwtProvider;
import com.example.demo.social.requestReponseDto.SignInResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private SignInResponseDto signInResDto;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String accessToken = jwtProvider.createAccessToken(customOAuth2User.getUserId());
//        String refreshToken = jwtProvider.createRefreshToken(user_identify_id);

        //accesstoken localstorage에 저장하기 위한 임시 dto설정
        //signInResDto = new SignInResponseDto(accessToken);

        //access cookie
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setMaxAge(60 * 60); // 1 days
        accessTokenCookie.setPath("/");    //특정 경로에서 유효한지 확인하는 메소드
        response.addCookie(accessTokenCookie);

//        // refresh cookie
//        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
//        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setMaxAge(1 * 24 * 60 * 60); // 1 days
//        refreshTokenCookie.setPath("/");    //특정 경로에서 유효한지 확인하는 메소드
//        response.addCookie(refreshTokenCookie);

//        //ajax 통신할때 설정했던 contentType작성 했던 것과 같음. 백앤드에서 선언
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(jsonResponse);

        response.sendRedirect("http://localhost:3000/returnCookie");
    }
}
