package com.example.demo.social.config;

import com.example.demo.social.handler.OAuth2SuccessHandler;
import com.example.demo.social.OAuth2.OAuth2UserServiceImpl;
import com.example.demo.social.filer.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2UserServiceImpl oauth2UserServiceImpl;
    private final OAuth2SuccessHandler oauth2SuccessHandler;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(CsrfConfigurer::disable)  //사이트의 요청을 어떻게 할것인가
                .httpBasic(HttpBasicConfigurer::disable)    //유저 아이디, 비번만해서 하는 기본 인증 안함
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   //세션 사용하지 않음
                .authorizeHttpRequests(request -> request.requestMatchers("/", "/api/**", "/oauth2/**").permitAll()   //우리 userService requestMapping
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/")
                        .authorizationEndpoint(endpoint ->  endpoint.baseUri("/api/user/login/oauth2"))    //내가 원하는 위치로 보냄 : 강사("/api/v1/auth/oauth2")
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/api/login/oauth2/code/*"))
                        .successHandler(oauth2SuccessHandler)
                        .userInfoEndpoint(endpoint -> endpoint.userService(oauth2UserServiceImpl))
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new FailAuthenticationEntryPoint()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //모든 origin, method, Header에 대해 허용
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

//authorizeHttpRequests 인가를 실패할 때 exceptionHandling에서 발동
class FailAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //{"code": "NP", "message":"No Permession."}
        response.getWriter().write("{\"code\": \"NP\", \"message\":\"No Permession.\"}");
    }
}

