package com.example.demo.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "친구채팅 데이터 옮겨주는 dto")
public class ChattingDto {
        @Schema(description = "1대1 채팅 보낸 사람", requiredMode = Schema.RequiredMode.AUTO, example = "101")
        private Long senderId;
        @Schema(description = "1대1 채팅 받는 사람", requiredMode = Schema.RequiredMode.AUTO, example = "100")
        private Long receiverId;
        @Schema(description = "데이터 처리용 column")
        private Long userId1;  // 수정: userId1 추가
        @Schema(description = "데이터 처리용 column")
        private Long userId2;  // 수정: userId2 추가
        @Schema(description = "메세지 내용", requiredMode = Schema.RequiredMode.AUTO, example = "안녕")
        private String messageContent;
        @Schema(description = "메세지 작성 시간", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2024-09-08 14:16:38.552634")
        private LocalDateTime timestamp;

}
