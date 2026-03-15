package com.example.demo.user;

import com.example.demo.user.model.AuthUserDetails;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("서비스 코드 실행");

        //  OAuth2 로그인 실행
        //  카카오에서 이걸 실행 했을 때 로그인 실패했으면 애초에 이 로직을 안 타짐
        //  OAuth2User가 받아와졌다는 것은 로그인에 성공했다는 것
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //  (밑부터 쭉) 이 작업 필터가 성공 로직
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String providerId = ((Long)attributes.get("id")).toString();
        System.out.println(providerId);

        String email = providerId + "@kakao.social";
        Map properties = (Map) attributes.get("properties");
        String name = (String) properties.get("nickname");

        //  DB에 회원이 있나 없나 확인
        Optional<User> result = userRepository.findByEmail(email);

        //  없으면 가입 시켜주기
        User user = null;
        if(!result.isPresent()) {
            userRepository.save(
                    User.builder()
                            .email(email)
                            .name(name)
                            .password("kakao")
                            .enable(true)
                            .role("ROLE_USER")
                            .build()
            );

            //  회원가입이 되어있든 안 되어있든 AuthUserDetails로 반환 그러면
            //  OAuth2AuthenticationSuccessHandler의 성공 로직을 타게된다.
            //  DB에 KAKAO로 이미 가입되어 있기에 else 로직을 타게된다.
            //  OAuth2AuthenticationSuccessHandler에서는 성공을 한 후 응답을 처리
            return AuthUserDetails.from(user);
        }
        //  있으면 해당 사용자 반환
        else {
            user = result.get();

            return AuthUserDetails.from(user);
        }
    }
}
