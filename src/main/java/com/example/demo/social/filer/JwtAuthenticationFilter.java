package com.example.demo.social.filer;

import com.example.demo.social.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String token = parseBearerToken(request);
            System.out.println("프론트에서 준 accesstoken??:"+token);

            if(token == null){
                filterChain.doFilter(request, response);
                return;
            }

            Long user_id = jwtProvider.validate(token);
            System.out.println("에서 검증 후 추출 아이디?:"+user_id);

            if(user_id == null){
                System.out.println("혹시");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;
            }

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            List<GrantedAuthority> authoritiesRole = Collections.emptyList();

            //유저정보를 저장함(다음 형식을 매개변수로 받음) : [email, passwd] : email, null
            AbstractAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user_id, token, authoritiesRole);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("혹시");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
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
