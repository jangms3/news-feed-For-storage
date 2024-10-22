package com.sparta.newsfeed.friend.service;//package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.entity.Friend;
import com.sparta.newsfeed.entity.FriendshipStatus;
import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.friend.dto.FriendResponseDto;
import com.sparta.newsfeed.friend.repository.FriendRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createFriends(Long toId) throws Exception {

        // 현재 로그인 되어 있는사람
        // -----------------------------------------------------------------------//
        // 회원으로 검증받은 id를 넣을것!
        Long fromId = 1L;

        // 유저 정보를 모두 가져옴
        Users fromUser = userRepository.findById(fromId).orElseThrow(() -> new Exception("회원 조회 실패"));
        Users toUser = userRepository.findById(toId).orElseThrow(() -> new Exception("회원 조회 실패"));

        // 받는 사람측에 저장될 친구 요청
        Friend friendFrom = Friend.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .status(FriendshipStatus.RESPONSE_WAITING)
                .build();

        // 보내는 사람 쪽에 저장될 친구 요청
        Friend friendTo = Friend.builder()
                .fromUser(toUser)
                .toUser(fromUser)
                .status(FriendshipStatus.REQUEST_WAITING)
                .build();

        // 저장
        friendRepository.save(friendTo);
        friendRepository.save(friendFrom);
    }

    // 친구 삭제
    public void deleteFriends(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        friendRepository.deleteById(id);
    }

//     대기 친구요청 조회
    @Transactional
    public List <FriendResponseDto> getWaitingFriendList() throws Exception {
//     현재 로그인한 유저의 정보를 불러온다
//     --------------------------------------------------------------------------------
//     로그인한 유저 정보를 넣기
        long userId = 1L;
        Users users = userRepository.findById(userId).orElseThrow(()-> new Exception("회원 조회 실패"));
        List<Friend> waitfriendsList = friendRepository.findByFromUserAndStatus(users, FriendshipStatus.RESPONSE_WAITING);
        return waitfriendsList.stream()
                .map(FriendResponseDto::to)
                .toList();
        //조회된 결과 객채를 담을 Dto 리스트
        // 결과 반환
    }

    // 친구 목록 조회
    @Transactional
    public List <FriendResponseDto> getAcceptedFriendList() throws Exception {
        long userId = 1L;
        Users users = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("회원 조회 실패"));
        List <Friend> friendsList = friendRepository.findByFromUserAndStatus(users, FriendshipStatus.ACCEPT);
        return friendsList.stream()
                .map(FriendResponseDto::to)
                .toList();
    }

    @Transactional
    public void approveFriendRequest(Long friendId) throws Exception {
        // 누를 친구 요청과 매칭되는 상대방 친구 요청 둘다 가져옴
        Long myId = 1L;
        Users user = userRepository.findById(friendId).orElseThrow(() -> new Exception("친구 요청 조회 실패"));

        friendRepository.findByFromUserIdAndToUserId(myId, friendId);

        Friend myself = friendRepository.findByFromUserIdAndToUserId(myId, friendId).orElseThrow(() -> new Exception("친구로부터의 요청이 없습니다."));
        Friend friend = friendRepository.findByFromUserIdAndToUserId(friendId, myId).orElseThrow(() -> new Exception("친구로부터의 요청이 없습니다."));

        // 둘다 상태를 ACCEPT로 변경
        myself.setStatus(FriendshipStatus.ACCEPT);
        friend.setStatus(FriendshipStatus.ACCEPT);
    }
}