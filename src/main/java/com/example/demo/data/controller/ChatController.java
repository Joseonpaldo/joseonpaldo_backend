//package com.example.demo.data.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class ChatController {
//    private final FriendLogRepository friendLogRepository;
//
//    @PutMapping("/friendChat")
//    public ResponseEntity<Void> friendChatPut(@RequestParam String from_id,
//                                              @RequestParam String to_id,
//                                              @RequestParam String chat_log) {
//        FriendLogEntity entity= FriendLogEntity.builder().
//                fromId(from_id).
//                toId(to_id).
//                chatLog(chat_log).
//                build();
//
//        friendLogRepository.insertLog(entity);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("/friendChat")
//    public List<FriendLogEntity> getFriendChat(@RequestParam String from_id, @RequestParam String to_id) {
//        List<FriendLogEntity> list=friendLogRepository.findLogList(from_id,to_id);
//        if(list==null){
//            return new ArrayList<>();
//        }
//        return list;
//    }
//
//}
