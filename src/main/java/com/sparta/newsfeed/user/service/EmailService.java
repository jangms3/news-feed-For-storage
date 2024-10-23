package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.entity.Users;
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

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, clientMail);
            message.setSubject(subject);
            message.setText(body);
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

        if (!userInputNumber.equals(VerificationCode)) {
            throw new IllegalArgumentException("인증 번호가 일치하지 않습니다.");
        } else {
            user.setVerified(true);
            return true;
        }
    }
}
