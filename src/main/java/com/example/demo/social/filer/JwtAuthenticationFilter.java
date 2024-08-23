package com.example.demo.social.filer;

import com.example.demo.social.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try{
            String token = parseBearerToken(request);
            if(token == null){
                filterChain.doFilter(request, response);
                return;
            }

            String userIdentifyId = jwtProvider.validate(token);

            if(userIdentifyId != null){
                return;
            }

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

            //유저정보를 저장함(다음 형식을 매개변수로 받음) : [email, passwd] : email, null
            AbstractAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userIdentifyId, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);

        }catch (Exception e){
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        //hasText : 0, null, writeText attack 확인하여 처리해주는 용도
        boolean hasAuthorization = StringUtils.hasText(authorization);

        if(!hasAuthorization) {
            return null;
        }
        //Bearer" " 로 시작하는가?
        boolean isBearer = authorization.startsWith("Bearer ");
        if(!isBearer) {
            return null;
        }

        String token = authorization.substring(7);
        return token;
    }
}
