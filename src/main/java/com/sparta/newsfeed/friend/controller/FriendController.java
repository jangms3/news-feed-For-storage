package com.sparta.newsfeed.friend.controller;//package com.sparta.newsfeed.friend.controller;

import com.sparta.newsfeed.friend.dto.FriendResponseDto;
import com.sparta.newsfeed.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 친추
    @PostMapping("/{userId}/add")
    public ResponseEntity<Void> createFriends(@PathVariable ("userId") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    // 친삭
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity <String> deleteFriends(@PathVariable ("userId") Long id) {
        friendService.deleteFriends(id);
        return ResponseEntity.ok("친삭 성공");
        //인가 확인 필요
    }

    // 대기친구조회
    @GetMapping("/waited")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<FriendResponseDto>> getWaitingFriendList() throws Exception {
        List<FriendResponseDto> response = friendService.getWaitingFriendList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 친구목록조회
    @GetMapping("/accepted")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<FriendResponseDto>> getAcceptedFriendList() throws Exception {
        List<FriendResponseDto> response = friendService.getAcceptedFriendList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //친구 수락
    @PutMapping ("/{userId}/approve")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <String> approveFriendRequest (@PathVariable ("userId") Long id) throws Exception {
        try {
            friendService.approveFriendRequest(id);
            return new ResponseEntity<>("친구 요청 수락", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
