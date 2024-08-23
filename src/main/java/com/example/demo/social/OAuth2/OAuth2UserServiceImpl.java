package com.example.demo.social.OAuth2;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("User attributes : "+oAuth2User.getAttributes());

        String oauth2ClientName = userRequest.getClientRegistration().getClientName();
        String accessToken = userRequest.getAccessToken().getTokenValue();

        System.out.println(" aT : "+accessToken);

        UserEntity userEntity;
        String user_identify_id = null;
        String email = null;
        String profile = null;
        String social_provider = null;

        if(oauth2ClientName.equals("kakao")){
            Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());   // attributes는 읽기전용 해쉬맵 -> 해쉬맵 복사후 추출
            Map<String, String> kakao_account = (Map<String, String>) attributes.get("kakao_account");  // email이 들어있는 부분
            Map<String, String> properties = (Map<String, String>) attributes.get("properties");        // profile_image들어있는 부분

            user_identify_id = attributes.get("id").toString();
            email = kakao_account.get("email");
            profile = properties.get("profile_image");
            social_provider = "kakao";
        }
        else if(oauth2ClientName.equals("naver")){
            //네이버는 알아서 map형식으로 return 시켜줌
            Map<String, String> responesMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            System.out.println(oAuth2User.getAttributes());

            user_identify_id = responesMap.get("id").substring(0, 14);
            email = responesMap.get("email");
            profile = responesMap.get("profile_image");
            social_provider = "naver";
        }
        else if(oauth2ClientName.equals("google")){
            Map<String, Object> attributes = oAuth2User.getAttributes();

            user_identify_id = attributes.get("sub").toString();  // Google의 고유 사용자 ID
            email = attributes.get("email").toString();
            profile = attributes.get("picture").toString();
            social_provider = "google";
        }

        userEntity = new UserEntity(user_identify_id, email, profile, social_provider);

        System.out.println(userEntity);
        userService.loginUser(userEntity);
        Long user_id=userEntity.getUser_id();
        System.out.println(user_id);

        return new CustomOAuth2User(user_identify_id);
    }
}
