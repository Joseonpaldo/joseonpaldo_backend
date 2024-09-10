package com.example.demo.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;
import com.example.demo.security.filter.tools.HttpClientTools;
import com.example.demo.security.jwt.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final HttpClientTools httpClientTools;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JWT AUTH FILTER : START");
        System.out.println(request.getRequestURI());

        // Get the access token
        String authHeader = request.getHeader("Authorization");
        String accessToken = null;
        String path=request.getRequestURI();
        if(path.startsWith("/api/swagger-ui") || path.startsWith("/api/v3/api-docs")){
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }

        // Get the refresh token
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (accessToken !=null) {
            // Validattion Process for Access Token
            System.out.println("JwtAuthFilter");
            String username = null;
            if (jwtProvider.validateToken(accessToken)) {
                System.out.println("JwtAuthFilter - validateToken");
                username = jwtProvider.getClaimsFromToken(accessToken).get("user_id");
                if (!jwtProvider.checkTokenExpiration(accessToken)) {
                    // If the access token is expired, check the refresh token
                    if (jwtProvider.checkTokenExpiration(refreshToken)) {
                        // If the refresh token is valid, generate a new access token then proceed to the next filter
                        System.out.println("JwtAuthFilter - checkTokenExpiration - refresh token is valid : Generate new access token");
                        Map<String, String> claims = jwtProvider.getClaimsFromToken(refreshToken);
                        String newAccessToken = jwtProvider.createAccessToken(Long.parseLong(claims.get("user_id")), claims.get("provider"));

                        response.setHeader("accessToken", newAccessToken);
                    } else {
                        // If the refresh token is invalid, return error page - session expired
                        Map<String, String> claims = jwtProvider.getClaimsFromToken(refreshToken);
                        Long user_id = Long.parseLong(claims.get("user_id"));
                        UserEntity user = userService.getUser(user_id);

                        String provider = user.getSocialProvider();
                        String token = user.getProviderAccessToken();

                        httpClientTools.sendRequest(provider, token);
                        userService.removeProviderAccessToken(user_id);
                        // Need to create logout action -> social logout action required
                        response.sendRedirect("/error/session-expired");
                    }
                }
            } else {
                // If the access token is invalid, return error page - invalid token
                Map<String, String> claims = jwtProvider.getClaimsFromToken(refreshToken);
                Long user_id = Long.parseLong(claims.get("user_id"));
                UserEntity user = userService.getUser(user_id);

                String provider = user.getSocialProvider();
                String token = user.getProviderAccessToken();

                httpClientTools.sendRequest(provider, token);
                // Need to create logout action -> social logout action required
                response.sendRedirect("/error/invalid-token");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        System.out.println("JWT AUTH FILTER : END");
        filterChain.doFilter(request, response);
    }

}
