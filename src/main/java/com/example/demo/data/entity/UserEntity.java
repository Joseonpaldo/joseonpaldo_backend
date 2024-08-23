package com.example.demo.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;   //그냥 인덱스
    @Column(nullable = false, unique = true, name = "user_identify_id")
    private String userIdentifyId;
    private String email;   //요놈이 아이디
    @Column(nullable = false)
    private String nickname;
    @Column(updatable = false, name = "social_provider")
    private String socialProvider;
    @Column(name = "profile_image")
    private String profilePicture;
    @Column(name = "provider_accessToken")
    private String providerAccessToken;


    private int tot_2p;
    private int win_2p;
    private int tot_4p;
    private int win_4p;

    public UserEntity(String user_identify_id, String email, String profile_picture, String social_provider,String userName) {
        this.nickname=userName;
        this.userIdentifyId = user_identify_id;
        this.email = email;
        this.profilePicture = profile_picture;
        this.socialProvider = social_provider;
    }
}
