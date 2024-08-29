package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
	private final JwtAuthFilter jwtAuthFilter;
	private final OAuth2SuccessHandler oauth2SuccessHandler;
	private final CustomLogoutSuccessHandler logoutSuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf
				.disable() // CSRF protection is not needed as we are using JWT tokens
			)
			.cors(cors -> cors
				.configurationSource(request -> {
					CorsConfiguration corsConfiguration = new CorsConfiguration();
					corsConfiguration.addAllowedOrigin("https://joseonpaldo.site");
					corsConfiguration.addAllowedMethod("*");
					return corsConfiguration;
				})
			)
			.httpBasic(httpBasic -> httpBasic
				.disable()
			)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(sessionManagement -> sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(request -> request
				.requestMatchers("/api/health").permitAll()
				.anyRequest().authenticated())
			.oauth2Login(oauth2Login -> oauth2Login
				.authorizationEndpoint(
					endpoint -> endpoint.baseUri("/api/login/oauth2"))
				.redirectionEndpoint(endpoint -> endpoint.baseUri("/api/login/oauth2/callback"))
				.successHandler(oauth2SuccessHandler)
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