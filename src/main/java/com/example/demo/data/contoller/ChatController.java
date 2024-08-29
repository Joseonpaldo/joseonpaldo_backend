package com.example.demo.data.contoller;

import com.example.demo.data.entity.FriendLogEntity;
import com.example.demo.data.repository.FriendLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    private final FriendLogRepository friendLogRepository;

    @PutMapping("/friendChat")
    public ResponseEntity<Void> friendChatPut(@RequestParam String from_id,
                                              @RequestParam String to_id,
                                              @RequestParam String chat_log) {
        FriendLogEntity entity= FriendLogEntity.builder().
                from_id(from_id).
                to_id(to_id).
                chat_log(chat_log).
                build();

        friendLogRepository.insertLog(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/friendChat")
    public List<FriendLogEntity> getFriendChat(@RequestParam String from_id, @RequestParam String to_id) {
        return friendLogRepository.findLogList(from_id,to_id);
    }

}
