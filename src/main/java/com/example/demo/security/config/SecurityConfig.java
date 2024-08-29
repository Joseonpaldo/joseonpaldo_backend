package com.example.demo.security.config;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.web.util.WebUtils;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.demo.security.filter.JwtAuthFilter;
import com.example.demo.security.handler.CustomLogoutSuccessHandler;
import com.example.demo.security.handler.OAuth2SuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthFilter jwtAuthFilter;
	private final OAuth2SuccessHandler oauth2SuccessHandler;
	private final CustomLogoutSuccessHandler logoutSuccessHandler;

	private String SECRET_KEY = "forsession";
	private long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 days

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(CsrfConfigurer::disable)
			.cors(cors -> cors
				.configurationSource(request -> {
					CorsConfiguration corsConfiguration = new CorsConfiguration();
					corsConfiguration.addAllowedOrigin("https://joseonpaldo.site");
					corsConfiguration.addAllowedMethod("*");
					return corsConfiguration;
				})
			)
			.httpBasic(HttpBasicConfigurer::disable)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(sessionManagement -> sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
			.authorizeHttpRequests(request -> request
				.requestMatchers("/api/health", "/api/login/oauth2/callback/*", "/").permitAll()
				.anyRequest().authenticated())
			.oauth2Login(oauth2Login -> oauth2Login
				.authorizationEndpoint(
					endpoint -> {
						endpoint.baseUri("/api/login/oauth2")
						.authorizationRequestRepository(authorizationRequestRepository());
						System.out.println("OAuth2 Login Endpoint: " + endpoint);
					}
				)
				.redirectionEndpoint(endpoint -> endpoint.baseUri("/api/login/oauth2/callback/*"))
				.successHandler(oauth2SuccessHandler)
				.failureHandler((request, response, exception) -> {
						System.err.println("OAuth2 Login Error: ");
						exception.printStackTrace();
						response.sendRedirect("/");
					}
				)
			)
			.logout(logout -> logout
				.logoutUrl("/api/logout")
				.logoutSuccessHandler(logoutSuccessHandler)
				.deleteCookies("RefreshToken")
				.logoutSuccessUrl("/")
			);
		return http.build();
	}

	@Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new AuthorizationRequestRepository<OAuth2AuthorizationRequest>() {

            private static final String OAUTH2_STATE_COOKIE = "OAUTH2_STATE";

            @Override
            public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
                Cookie cookie = WebUtils.getCookie(request, OAUTH2_STATE_COOKIE);
                if (cookie != null) {
                    String state = cookie.getValue();
                    return decodeJwt(state);
                }
                return null;
            }

            @Override
            public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                                 HttpServletRequest request, HttpServletResponse response) {
                if (authorizationRequest != null) {
                    String state = encodeJwt(authorizationRequest);
                    Cookie cookie = new Cookie("OAUTH2_STATE_COOKIE", state);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true); // HTTPS를 사용하는 경우
                    response.addCookie(cookie);
                }
            }

            @Override
            public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
                Cookie cookie = WebUtils.getCookie(request, OAUTH2_STATE_COOKIE);
                if (cookie != null) {
                    String state = cookie.getValue();
                    cookie.setMaxAge(0); // 쿠키 만료 설정
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true); // HTTPS를 사용하는 경우
                    response.addCookie(cookie);
                    return decodeJwt(state);
                }
                return null;
            }
        };
    }

    private String encodeJwt(OAuth2AuthorizationRequest authorizationRequest) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestJson = mapper.writeValueAsString(authorizationRequest);
			Map<String, String> claims = new HashMap<>();
			claims.put("authorization_request", requestJson);

			ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

			String jwt = Jwts.builder()
							.setClaims(claims)
							.setSubject("OAuth2AuthorizationRequest")
							.setExpiration(Date.from(now.toInstant().plusMillis(EXPIRATION_TIME)))
							.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
							.compact();

            return jwt;
        } catch (Exception e) {
            throw new RuntimeException("Failed to encode OAuth2AuthorizationRequest", e);
        }
    }

    private OAuth2AuthorizationRequest decodeJwt(String jwt) {
        try {
            String requestJson = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .get("authorization_request", String.class);
    
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(requestJson, OAuth2AuthorizationRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode OAuth2AuthorizationRequest", e);
        }
    }
}