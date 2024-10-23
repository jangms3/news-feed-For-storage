package com.sparta.newsfeed.user.UsersUtil;

import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersUtil {
    private final UserRepository userRepository;

    public Users findById(Long Userid) {
        return userRepository.findById(Userid).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 없습니다")
        );
    }
}
