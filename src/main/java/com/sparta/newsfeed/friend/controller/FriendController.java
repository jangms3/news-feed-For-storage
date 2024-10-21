package com.sparta.newsfeed.friend.controller;//package com.sparta.newsfeed.friend.controller;

import com.sparta.newsfeed.friend.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    //친추
    @PostMapping("/{userId}/add")
    public ResponseEntity<Void> createFriends(@PathVariable ("userId") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    //친삭
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity <String> deleteFriends(@PathVariable ("userId") Long id) {
        friendService.deleteFriends(id);
        return ResponseEntity.ok("친삭 성공");
    }

    //대기친구조회
    @GetMapping("/recieved")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getWaitingFriendInfo() throws Exception {
        return friendService.getWaitingFriendList();
    }

    //친구 수락
    @PostMapping("/{userId}/approve")
    @ResponseStatus(HttpStatus.OK)
    public String approveFriend(@PathVariable ("userId") Long id) throws Exception {
        return friendService.approveFriendRequest(id);
    }
}
