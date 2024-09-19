package com.yunha.backend.security.jwt;

import com.yunha.backend.entity.User;
import com.yunha.backend.security.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if ("/join".equals(path) || "/login".equals(path) || path.startsWith("/uploads")) {
            filterChain.doFilter(request, response);
            return;
        }

//        System.out.println("토큰 : "+request.getHeader("Authorization"));
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")){
            System.out.println("token이 없거나, Bearer가 포함되어 있지 않습니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"잘못된 요청입니다.");
            return;
        }
        String token = authorization.split(" ")[1];
        //토큰의 소멸되었는지 검사하기
        if(jwtUtil.isExpired(token)){
            System.out.println("token Expire 상태입니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"로그인이 만료되었습니다.");
            return;
        }
        //여기까지 오면 토큰이 정상이다.
        //토큰에서 아이디와, 권한 획득
        Long accountCode = jwtUtil.getUserCode(token);
        String accountId = jwtUtil.getUsername(token);
        String accountRole = jwtUtil.getRole(token);
        String nickname = jwtUtil.getUserNickname(token);


        User user = new User();
        user.setUserId(accountId);
        user.setUserRole(accountRole);
        user.setUserCode(accountCode);
        user.setUserNickname(nickname);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request,response);
    }
}
