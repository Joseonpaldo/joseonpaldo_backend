package com.example.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChattingDto {

        private Long senderId;
        private Long receiverId;
        private Long userId1;  // 수정: userId1 추가
        private Long userId2;  // 수정: userId2 추가
        private Long roomId;
        private String messageContent;
        private LocalDateTime timestamp;

        public ChattingDto(Long roomId) {
                this.roomId = roomId;
        }

        public ChattingDto(Long userId, Long userId2, String messageContent, LocalDateTime sentAt) {
                this.userId1 = userId;
                this.userId2 = userId2;
                this.messageContent = messageContent;
                this.timestamp = sentAt;
        }
}
