package com.sparta.newsfeed.friend.service;//package com.sparta.newsfeed.friend.service;

import com.sparta.newsfeed.entity.Friend;
import com.sparta.newsfeed.entity.FriendshipStatus;
import com.sparta.newsfeed.entity.Users;
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

    // 받은 친구요청 조회
//    @Transactional
//    public ResponseEntity<?> getWaitingFriendList() throws Exception {
    // 현재 로그인한 유저의 정보를 불러온다
    // --------------------------------------------------------------------------------//
    // 로그인한 유저 정보를 넣기
//        Users users = userRepository.findById(-----.getLoginId()).orElsethrow(() -> new Exception("회원 조회 실패"));
//        List<Friend> friendsList = users.getFriendList();
//
//        //조회된 결과 객채를 담을 Dto 리스트
//        List<WaitingFriendListDto> result = new ArrayList<>();
//
//        for (Friend x : friendsList) {
//            // 수락 대기중인 요청만 조회
//            if (!x.isFrom() && x.getStatus() == Friend.WATTING) {
//                Users friend = usersRepository.findById(x.getId()).orElseThrow(() -> new Exception("회원 조회 실패"));
//                WaitingFriendListDto dto = WaitingFriendListDto.builder()
//                        .friendId(x.getId())
//                        .friendEmail(friend.getEmail())
//                        .friendName(friend.getName())
//                        .status(x.getStatus())
//                        .build();
//                result.add(dto);
//            }
//        }
//        // 결과 반환
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    public String approveFriendRequest(Long id) throws Exception {
//        // 누를 친구 요청과 매칭되는 상대방 친구 요청 둘다 가져옴
//        Friend friend = friendRepository.findById(id).orElseThrow(() -> new Exception("친구 요청 조회 실패"));
//        Friend counterFriend = friendRepository.findById(friend.getCounterpartId()).orElseThrow(() -> new Exception("친구 요청 조회"));
//
//        // 둘다 상태를 ACCEPT로 변경
//        friend.acceptFriendRequest();
//        counterFriend.acceptFriendRequest();
//
//        return "승인 성공" ;
//    }
//}
}