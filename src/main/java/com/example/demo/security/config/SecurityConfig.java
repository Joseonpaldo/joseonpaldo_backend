package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.demo.security.filter.JwtAuthFilter;
import com.example.demo.security.handler.CustomLogoutSuccessHandler;
import com.example.demo.security.handler.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final OAuth2SuccessHandler oauth2SuccessHandler;
	private final CustomLogoutSuccessHandler logoutSuccessHandler;
	private final JwtAuthFilter JwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(CsrfConfigurer::disable)
			.cors(cors -> cors
				.configurationSource(request -> {
					CorsConfiguration corsConfiguration = new CorsConfiguration();
					corsConfiguration.addAllowedOrigin("*");
					corsConfiguration.addAllowedMethod("*");
					return corsConfiguration;
				})
			)
			.httpBasic(HttpBasicConfigurer::disable)
			.sessionManagement(sessionManagement -> sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(JwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(request -> request
				.requestMatchers("/api/health", "/api/login/oauth2/callback/*", "/", "returnCookie").permitAll()
				.anyRequest().authenticated())
			.oauth2Login(oauth2Login -> oauth2Login
				.authorizationEndpoint(
					endpoint -> {
						System.out.println("OAuth2 Login Endpoint: " + endpoint);
						endpoint.baseUri("/api/login/oauth2");
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
}