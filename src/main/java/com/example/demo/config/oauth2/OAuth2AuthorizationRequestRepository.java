package com.example.demo.config.oauth2;

import com.example.demo.utils.Aes256;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class OAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("OAUTH2_REQUEST")){
                OAuth2AuthorizationRequest oAuth2AuthorizationRequest =
                        (OAuth2AuthorizationRequest)SerializationUtils.deserialize(cookie.getValue().getBytes());

                return oAuth2AuthorizationRequest;
            }
        }
        return null;
    }

    //  첫 번째 요청이 들어왔을 때 이 메소드가 실행되면서 사용자 웹 브라우저에 쿠키에 저장될 수 있게 설정
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        //  여기다가 쿠키 세팅하게 해주면 된다.
        Cookie cookie = new Cookie(
                "OAUTH2_REQUEST",
                Aes256.encrypt(SerializationUtils.serialize(authorizationRequest)) // 이걸 이용해서 쿠키 세팅할 때 암호화해서 해줌
        );
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(((int) Duration.ofSeconds(300L).toSeconds()));

        response.addCookie(cookie);
    }


    //  삭제 처리
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("OAUTH2_REQUEST")){
                OAuth2AuthorizationRequest oAuth2AuthorizationRequest =
                        (OAuth2AuthorizationRequest) SerializationUtils.deserialize(
                                Aes256.decrypt(cookie.getValue().getBytes(StandardCharsets.UTF_8))
                        );

                cookie.setValue("");
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setMaxAge(((int) Duration.ofSeconds(0L).toSeconds()));    //  쿠키 만료 시간이 지나면 웹 브라우저가 알아서 삭제
                response.addCookie(cookie);

                return oAuth2AuthorizationRequest;
            }
        }

        return null;
    }
}
