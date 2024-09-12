package com.example.demo.data.controller;

import com.example.demo.data.entity.FriendRelationEntity;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.ChatRoomRepository;
import com.example.demo.data.repository.FriendRelationRepositoryImpl;
import com.example.demo.data.repository.UserRepositoryImpl;
import com.example.demo.data.service.UserService;
import com.example.demo.security.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag(name = "친구 관련 메소드 api controller", description = "친구 추가, 삭제, 확인 등의 메소드를 제공하는 api controller")
@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendRelationController {
    private final FriendRelationRepositoryImpl friendRelationRepository;
    private final UserRepositoryImpl userRepository;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;

    @Operation(operationId = "friendCheck",summary = "현재 친구인지 확인하기",
            description = "친구 추가 및 삭제를 할때 현재 친구로 등록되어 있는지 확인하는 메소드",
            security = {@SecurityRequirement(name = "custom-auth-token")},
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "false면 이미 친구인 상태, true면 친구가 아닌 상태",
                    content = @Content(
                            schema = @Schema(implementation = Boolean.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "로그인 되지 않은 상태에서의 요청 혹은 엑세스 토큰의 expire",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            )
    })
    @PostMapping("/check/{user_id}/{friend_id}")
    public boolean listCheck(@PathVariable Long user_id, @PathVariable Long friend_id){
        // 자기 자신을 친구로 추가하는 상황 방지
        if (user_id.equals(friend_id)) {

            System.out.println("false");
            return false; // 자기 자신을 친구로 추가할 수 없으므로 false 반환
        }
        // 친구 관계가 있으면 true, 없으면 false

        System.out.println("true");
        return friendRelationRepository.countByUserId1AndUserId2OrUserId1AndUserId2(user_id, friend_id, friend_id, user_id) == 1;
    }

    @Operation(operationId = "getFriendList",summary = "친구 목록 얻어오기",
            description = "JWT를 받아서 해당 유저아이디의 친구목록을 반환",
            security = {@SecurityRequirement(name = "custom-auth-token")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "친구목록을 반환한다."
                            , content = @Content(
                            schema = @Schema(type = "array", implementation = UserEntity.class)
                    )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "로그인 되지 않은 상태에서의 요청 혹은 엑세스 토큰의 expire",
                            content = @Content(
                                    schema = @Schema(implementation = void.class)
                            )
                    )
            })
    @GetMapping("/list/{jwt}")
    public List<UserEntity> getList(@PathVariable String jwt){
        Map<String, String> map = jwtProvider.getClaimsFromToken(jwt);
        Long user_id = Long.parseLong(map.get("user_id"));

        List<FriendRelationEntity> relationList = friendRelationRepository.findByUserId1OrUserId2(user_id, user_id);

        //열에서 내 값 지우고 친구 id만 뽑아 리스트 저장
        List<Long> extractedFriendsId = relationList.stream()
                .flatMap(relation -> {
                    if (relation.getUserId1().equals(user_id)) {
                        return Stream.of(relation.getUserId2());
                    } else if (relation.getUserId2().equals(user_id)) {
                        return Stream.of(relation.getUserId1());
                    }
                    return Stream.empty();
                })
                .collect(Collectors.toList());

        List<UserEntity> friendList = new ArrayList<>();

        //뽑아낸 id로 userEntity 리스트 만들어 저장
        for(Long id : extractedFriendsId){
            friendList.add(userRepository.findByuserId(id));
        }

        System.out.println("friendController3: " + friendList);

        return friendList;
    }

    @Operation(operationId = "addFriend",summary = "친구 추가하기",
    description = "유저아이디와 친구아이디를 받아서 친구 추가를 한다.",
            security = {@SecurityRequirement(name = "custom-auth-token")},
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "친추 성공",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "로그인 되지 않은 상태에서의 요청 혹은 엑세스 토큰의 expire",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "없는 유저의 인덱스가 parameter 로 들어오는 에러, 하지만 내부적으로는 검증 후 요청 보낸다."
            )
    })
    @PutMapping("/add/{userId}/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId){
        userService.addFriend(userId,friendId);
    }

    @Operation(operationId = "deleteFriend", summary = "친구관게를 끊기",
    description = "유저아이디와 친구아이디를 받아서 친구관계를 삭제하는 메소드",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = void.class)
                    )
            )
    })
    @DeleteMapping("/delete/{userId}/{friendId}")
    public void deleteFriend(@PathVariable Long userId, @PathVariable Long friendId){

        System.out.println("user" + userId + "friend" + friendId);
        System.out.println("삭제완료");
        // 서로 친구 관계 삭제
        userService.deleteFriend(userId, friendId);
    }

}