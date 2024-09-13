package com.example.demo.data.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "유저 정보 entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "유저 인덱스", requiredMode = Schema.RequiredMode.REQUIRED, example = "101")
    private Long userId;   //그냥 인덱스

    @Column(nullable = false, unique = true, name = "user_identify_id")
    @Schema(description = "소셜 로그인 provider 상 유저의 인덱스", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "3SySgmjxHcbx2IW5AeVW7SQ7O7Es0P7xlMzv9_BRdSU")
    private String userIdentifyId;

    @Schema(description = "유저 이메일", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "dw5817@naver.com")
    private String email;   //요놈이 아이디

    @Column(nullable = false)
    @Schema(description = "유저 이름", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "배동우")
    private String nickname;

    @Column(updatable = false, name = "social_provider")
    @Schema(description = "소셜로그인 provider", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "kakao")
    private String socialProvider;

    @Column(name = "profile_image")
    @Schema(description = "소셜로그인에서 제공해준 유저 프로필 사진, 클라우드 저장소에서 url로 따온 형식",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String profilePicture;

    @Column(name = "provider_access_token")
    @Schema(description = "소셜로그인 provider 측에서 제공한 accessToken",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String providerAccessToken;

    @Column(name = "tot2p")
    @Schema(description = "2인 게임 총 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "17")
    private int tot2p = 0;

    @Column(name = "win2p")
    @Schema(description = "2인 게임 승리 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "5")
    private int win2p = 0;

    @Column(name = "tot4p")
    @Schema(description = "4인 게임 총 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "20")
    private int tot4p = 0;

    @Column(name = "win4p")
    @Schema(description = "4인 게임 승리 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "4")
    private int win4p = 0;

    @Column(name = "win_rate2p")
    @Schema(description = "2인 게임 승률", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "29.41")
    private BigDecimal winRate2p;

    @Column(name = "win_rate4p")
    @Schema(description = "4인 게임 승률", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "20.00")
    private BigDecimal winRate4p;

    @PrePersist
    @PreUpdate
    private void calculate() {
        calculateWinRate2p();
        calculateWinRate4p();
    }

    private void calculateWinRate2p() {
        if (tot2p != 0) {
            this.winRate2p = new BigDecimal(win2p)
                                    .divide(new BigDecimal(tot2p), 2, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal(100));
        }
    }

    private void calculateWinRate4p() {
        if (tot4p != 0) {
            this.winRate4p = new BigDecimal(win4p)
                                    .divide(new BigDecimal(tot4p), 2, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal(100));
        }
    }
}
