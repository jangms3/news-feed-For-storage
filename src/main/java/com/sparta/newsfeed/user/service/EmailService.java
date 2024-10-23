package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.user.UsersUtil.UsersUtil;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private static final String senderEmail = "sparta.spring3@gmail.com";

    @Transactional
    public void sendEmail(String clientMail) {
        MimeMessage message = mailSender.createMimeMessage();
        String subject = "포스피드_newsfeed 이메일 인증";
        int verificationCode = (int) (Math.random() * 900000) + 100000;
        String body = "6자리 숫자를 입력해주세요: " + verificationCode;

        Users user = userRepository.findByEmail(clientMail).orElseThrow(() ->
                new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );
        user.setVerificationCode(verificationCode);
        // 관심사 분리: 유저 아이디와 verificationCode 를 갖는 별도의 테이블, 유효 기간을 설정 id,
        // 유효시간 내 인증을 받을 수 있도록
        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, clientMail); // to: 수신자 이메일 주소.
            message.setSubject(subject);  // subject: 이메일의 제목.
            message.setText(body);  // body: 이메일의 내용.
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }

    @Transactional
    public boolean verifyEmail(String clientMail, Integer userInputNumber) {
        Users user = userRepository.findByEmail(clientMail).orElseThrow(() ->
                new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );
        Integer VerificationCode = user.getVerificationCode();
        return userInputNumber.equals(VerificationCode)? true:false;
    }
}
