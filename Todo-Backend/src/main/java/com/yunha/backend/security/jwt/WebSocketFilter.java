package com.yunha.backend.security.jwt;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketFilter implements HandshakeInterceptor {
    private final JWTUtil jwtUtil;

    public WebSocketFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("여기 들어옴?");
        String path = request.getURI().toString();
        System.out.println(path);
        if (path.startsWith("http://localhost:8080/chat/")) {
            System.out.println("넘어가자");
            return true;
        }
        try{
            String BearerToken = request.getHeaders().get("Authorization").get(0);
            if(BearerToken == null || !BearerToken.startsWith("Bearer ")){
                System.out.println("token이 없거나, Bearer가 포함되어 있지 않습니다.");
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }
            String token = BearerToken.split(" ")[1];
            if(jwtUtil.isExpired(token)){
                System.out.println("토큰이 만료되었습니다.");
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }
            Long userCode = jwtUtil.getUserCode(token);
            attributes.put("userCode",userCode);
            System.out.println("유저 코드는 : "+userCode);
            return true;
        } catch (Exception e){
            return false;
        }


    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
