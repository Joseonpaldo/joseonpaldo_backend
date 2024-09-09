package com.example.demo.data.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatsDto {

    private String nickname;
    private int tot2p;
    private int win2p;
    private int tot4p;
    private int win4p;
    private BigDecimal winRate2p;
    private BigDecimal winRate4p;
}