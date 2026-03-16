package com.example.demo.config;

/*
기존 스프링 시큐리티의 로그인 처리 방식

1. UsernamePasswordAuthenticationFilter 에서 ID(username), PW(password) 를 받아서             (컨트롤러 역할)
2. UsernamePasswordAuthenticationToken 객체에 담아서                                          (Dto 역할)
3. AuthenticationManager 인터페이스를 상속받은 ProviderManager 객체의 authenticate 메소드 실행    (서비스 메소드 실행 역할)

4. 3번에서 실행된 메소드에서 AbstractUserDetailsAuthenticationProvider 객체의 authenticate 메소드 실행
5. 4번에서 실행된 메소드에서 retrieveUser 메소드 실행하고 retrieveUser메소드에서 InMemoryUserDetailsManager 객체의 loadUserByUsername 메소드 실행
6. loadUserByUsername 메소드에서 사용자 정보를 조회해서 해당 하는 사용자가 있으면 UserDetails 객체를 반환
7. 8. 9. 반환받은 걸 확인해서 세션에 사용자 인증 정보 저장
 */

/*

요청 바꾸기 : UsernamePasswordAuthenticationFilter의 attemptAuthentication 메소드를 재정의
사용자 확인 : UserDetailsService의 loadUserByUsername 메소드를 재정의
응답 바꾸기 : UsernamePasswordAuthenticationFilter의 successfulAuthentication 메소드 재정의
*/

import com.example.demo.config.filter.JwtFilter;
import com.example.demo.config.oauth2.OAuth2AuthenticationSuccessHandler;
import com.example.demo.config.oauth2.OAuth2AuthorizationRequestRepository;
import com.example.demo.user.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  //    세션을 저장할 필요없다

        http.oauth2Login(config -> {
            //  OAuth2AuthorizationRequestRepository 클래스 사용하라고 등록
            config.authorizationEndpoint(endpoint ->
                    endpoint.authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
            );
            config.userInfoEndpoint(
                    endpoint -> endpoint.userService(oAuth2UserService)
            );   // 사용자 정보를 받아오는 코드를 어디에다 짜겠다 클래스를 지정하는 것
            config.successHandler(oAuth2AuthenticationSuccessHandler);    //  성공했을 때 어떻게 처리하겠다

        });

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests(
                (auth) -> auth
                        .requestMatchers("/user/login","/login", "/user/signup", "/user/verify").permitAll()
                        .requestMatchers("/board/reg").authenticated()
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll()
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 적용
        return source;
    }
}
