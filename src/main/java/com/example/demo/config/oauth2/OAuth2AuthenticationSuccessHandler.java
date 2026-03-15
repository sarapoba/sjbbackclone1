package com.example.demo.config.oauth2;

import com.example.demo.user.model.AuthUserDetails;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //  성공을 한 후 응답을 처리
        System.out.println("OAuth 2.0 로그인 성공");

        AuthUserDetails user = (AuthUserDetails)authentication.getPrincipal();

        //  원래 로그인을 성공했을 때 토큰 발급을 해줬으니 발급하는 코드
        String jwt = jwtUtil.createToken(user.getIdx(), user.getUsername(), user.getRole());
        response.addHeader("Set-Cookie", "ATOKEN=" + jwt + "; Path=/");

        //  백엔드를 통해 로그인을 다 했으니 프론트엔드에서 로그인을 성공했을 때 가야하는 페이지로 리다이렉트 시켜줘야 함
        String redirectUrl = "http://localhost:5173/success";
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
