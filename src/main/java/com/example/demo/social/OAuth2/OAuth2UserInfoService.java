package com.example.demo.social.OAuth2;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.service.UserAccountService;
import com.example.demo.data.service.UserGameService;
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
public class OAuth2UserInfoService extends DefaultOAuth2UserService {
    private final UserGameService userGameService;
    private final UserAccountService userAccountService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("User attributes : "+oAuth2User.getAttributes());

        String oauth2ClientName = userRequest.getClientRegistration().getClientName();
        String providerAccessToken = userRequest.getAccessToken().getTokenValue();

        System.out.println(" aT : "+providerAccessToken);

        UserEntity userEntity;
        String user_identify_id = null;
        String userName=null;
        String email = null;
        String profile = null;
        String social_provider = null;

        if(oauth2ClientName.equals("kakao")){
            Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());   // attributes는 읽기전용 해쉬맵 -> 해쉬맵 복사후 추출

            System.out.println(oAuth2User.getAttributes());

            Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");  // email이 들어있는 부분
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");        // profile_image들어있는 부분

            user_identify_id = attributes.get("id").toString();
            email = (String) kakao_account.get("email");
            profile = (String) properties.get("profile_image");
            social_provider = oauth2ClientName;
            userName =(String) properties.get("nickname");
        }
        else if(oauth2ClientName.equals("naver")){
            Map<String, String> responesMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            System.out.println(oAuth2User.getAttributes());

            user_identify_id = responesMap.get("id");
            email = responesMap.get("email");
            profile = responesMap.get("profile_image");
            userName = responesMap.get("name");
            social_provider = "naver";
        }
        else if(oauth2ClientName.equals("google")){
            Map<String, Object> attributes = oAuth2User.getAttributes();
            System.out.println(attributes);
            user_identify_id = attributes.get("sub").toString();  // Google의 고유 사용자 ID
            userName=attributes.get("given_name").toString();
            email = attributes.get("email").toString();
            profile = attributes.get("picture").toString();
            social_provider = oauth2ClientName;
        }

        userEntity = new UserEntity(user_identify_id, email, profile, social_provider,userName, providerAccessToken);

        System.out.println(userEntity);
        Long userId =  userAccountService.loginUser(userEntity);
        System.out.println(userEntity);

        return new CustomOAuth2User(user_identify_id, userId);
    }
}
