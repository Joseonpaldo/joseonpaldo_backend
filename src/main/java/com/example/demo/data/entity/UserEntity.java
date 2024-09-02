package com.example.demo.data.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
    private Long userId;   //그냥 인덱스
    @Column(nullable = false, unique = true, name = "user_identify_id")
    private String userIdentifyId;
    private String email;   //요놈이 아이디
    @Column(nullable = false)
    private String nickname;
    @Column(updatable = false, name = "social_provider")
    private String socialProvider;
    @Column(name = "profile_image")
    private String profilePicture;
    @Column(name = "provider_access_token")
    private String providerAccessToken;

    private int tot2p;
    private int win2p;
    private int tot4p;
    private int win4p;

    @Column(precision = 5, scale = 2)
    private BigDecimal winRate2p;

    @Column(precision = 5, scale = 2)
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
        } else {
            this.winRate2p = BigDecimal.ZERO;
        }
    }

    private void calculateWinRate4p() {
        if (tot4p != 0) {
            this.winRate4p = new BigDecimal(win4p)
                                    .divide(new BigDecimal(tot4p), 2, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal(100));
        } else {
            this.winRate4p = BigDecimal.ZERO;
        }
    }
}
