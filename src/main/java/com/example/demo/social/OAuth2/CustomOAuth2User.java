package com.example.demo.social.OAuth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private String user_identify_id;
    @Getter
    private Long userId;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {

        return this.user_identify_id;
    }
}
