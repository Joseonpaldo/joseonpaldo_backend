package com.example.demo.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DB 상의 민감정보를 제외한 유저 정보를 제공하는 dto")
public class UserPrintDto {
    @Schema(description = "유저 인덱스", requiredMode = Schema.RequiredMode.REQUIRED, example = "101")
    private Long user_id;   //그냥 인덱스
    @Schema(description = "유저 이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
    example = "dw5817@naver.com")
    private String email;   //요놈이 아이디
    @Schema(description = "유저 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
    example = "배동우")
    private String nickname;
    @Schema(description = "소셜로그인 provider", requiredMode = Schema.RequiredMode.REQUIRED,
    example = "kakao")
    private String socialProvider;
    @Schema(description = "소셜로그인에서 제공해준 유저 프로필 사진, 클라우드 저장소에서 url로 따온 형식",
    requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String profilePicture;
    @Schema(description = "2인 게임 총 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "17")
    private int tot_2p;
    @Schema(description = "2인 게임 승리 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "5")
    private int win_2p;
    @Schema(description = "4인 게임 총 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "20")
    private int tot_4p;
    @Schema(description = "2인 게임 총 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "4")
    private int win_4p;
}