package com.example.demo.user.model;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    public static class SignupReq {
        @Pattern(message = "이메일 형식이 아닙니다."
                , regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        private String email;
        //  한글만 가능하게 설정
        @Pattern(message = "이름은 한글로 된 완성된 글자 2~10자여야 합니다.", regexp = "^[가-힣]{2,10}$")
        private String name;

        @Pattern(message = "비밀번호는 8~20자여야 하며, 영문, 숫자, 특수문자를 최소 하나씩 포함해야 합니다."
                , regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$")
        private String password;

        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
                    .enable(false)
//                    .role("ROLE_USER")  //    User 엔티티의 도메인 무결성 제약조건으로 처리
                    .build();
        }
    }


    @Builder
    @Getter
    public static class SignupRes {
        private Long idx;
        private String email;
        private String name;

        public static SignupRes from(User entity) {
            return SignupRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .build();
        }
    }

    @Getter
    public static class LoginReq {
        private String email;
        private String password;
    }

    @Builder
    @Getter
    public static class LoginRes {
        private Long idx;
        private String email;
        private String name;

        public static LoginRes from(User entity) {
            return LoginRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .build();
        }
    }
}
