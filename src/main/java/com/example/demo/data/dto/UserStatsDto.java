package com.example.demo.data.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "랭킹 페이지에서 승률 반환시키는 dto")
public class UserStatsDto {
    @Schema(description = "유저 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "배동우")
    private String nickname;
    @Schema(description = "2인 게임 총 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "17")
    private int tot2p;
    @Schema(description = "2인 게임 승리 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "5")
    private int win2p;
    @Schema(description = "4인 게임 총 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "20")
    private int tot4p;
    @Schema(description = "4인 게임 승리 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "4")
    private int win4p;
    @Schema(description = "2인 게임 승률", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "29.41")
    private BigDecimal winRate2p;
    @Schema(description = "4인 게임 승률", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "20.00")
    private BigDecimal winRate4p;
}