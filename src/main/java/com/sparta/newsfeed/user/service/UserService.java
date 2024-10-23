package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.config.PasswordEncoder;
import com.sparta.newsfeed.entity.UserRoleEnum;
import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.jwt.JwtUtil;
import com.sparta.newsfeed.user.otherDto.MyProfileResponseDto;
import com.sparta.newsfeed.user.otherDto.ProfileResponseDto;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.requestDto.LoginRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(String username, String pw, String email, boolean role) {
        checkUsername(username);
        String password = passwordEncoder.encode(pw);
        Optional<Users> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email이 존재합니다.");
        }

        UserRoleEnum checkedRole;
        if (role) {
            checkedRole = UserRoleEnum.ADMIN;
        } else {
            checkedRole = UserRoleEnum.USER;
        }

        Users user = new Users(username, password, email, checkedRole);
        userRepository.save(user);
    }

    public void login(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        Users user = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("등록된 사용자가 존재하지 않습니다.")
        );
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String token = jwtUtil.createToken(user.getEmail(), user.getRole());
        jwtUtil.addJwtCookie(token, res);
    }

    public MyProfileResponseDto getMyProfile(Long userId) {
        return new MyProfileResponseDto(findById(userId));
    }

    public ProfileResponseDto getProfile(Long userId) {
        return new ProfileResponseDto(findById(userId));
    }

    @Transactional
    public void updateUser(Long userId, String username, String introduction) {
        checkUsername(username);
        Users user = findById(userId);
        user.setUsername(username);
        user.setIntroduction(introduction);
    }

    public void delete(Long userId) {
        Users user = findById(userId);
        userRepository.deleteById(user.getId());
    }

    private void checkUsername(String username) {
        Optional<Users> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 이름이 존재합니다.");
        }
    }

    private Users findById(Long Userid) {
        return userRepository.findById(Userid).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 없습니다")
        );
    }

    public Boolean checkIdPw(String email, String password) {
        Users user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("이메일이 잘못되었습니다.")
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못 되었습니다");
        }
        return true;
    }
}
