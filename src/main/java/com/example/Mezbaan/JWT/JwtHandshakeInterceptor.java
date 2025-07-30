package com.example.Mezbaan.JWT;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtUtil jwtUtils;

    @Override
    public boolean beforeHandshake(@NonNull org.springframework.http.server.ServerHttpRequest request,@NonNull org.springframework.http.server.ServerHttpResponse response,@NonNull WebSocketHandler wsHandler,@NonNull Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();

            String token = httpServletRequest.getHeader("Authorization");

            if (token == null && httpServletRequest.getParameter("token") != null) {
                token = "Bearer " + httpServletRequest.getParameter("token");
            }

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                String username = jwtUtils.extractUsername(token);
                if (username != null && jwtUtils.validateToken(token)) {
                    attributes.put("username", username);
                    return true;
                }
            }
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(@NonNull org.springframework.http.server.ServerHttpRequest request,@NonNull org.springframework.http.server.ServerHttpResponse response,@NonNull WebSocketHandler wsHandler, Exception exception) {

    }
}