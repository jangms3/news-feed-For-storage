package com.sparta.newsfeed.filter;

import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.jwt.JwtUtil;
import com.sparta.newsfeed.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();

        // 인증이 필요없는 URL
        // READ all feeds
        // GET /api/feeds/{feedId}
        // signup
        // login
        if ( StringUtils.hasText(url) &&
                (( url.startsWith("/api/feeds/") && method.equals("GET") )
                        || url.startsWith("/api/user/signup")
                        || url.startsWith("/api/user/login")
                        || url.startsWith("/api/user/logout")
                )
        ) {
            // 해당 API 요청은 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
            if (StringUtils.hasText(tokenValue)) {
                String token = jwtUtil.substringToken(tokenValue);
                if (!jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }
                Claims info = jwtUtil.getUserInfoFromToken(token);
                Users user = userRepository.findByEmail(info.getSubject()).orElseThrow(() ->
                        new NullPointerException("유저를 찾을 수 없습니다.")
                );
                request.setAttribute("user", user);
                chain.doFilter(request, response);
            } else {
                throw new IllegalArgumentException("토큰을 찾을 수 없습니다.");
            }
        }
    }
}