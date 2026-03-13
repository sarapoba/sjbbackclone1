package com.example.demo.user;

import com.example.demo.user.model.AuthUserDetails;
import com.example.demo.user.model.UserDto;
import com.example.demo.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody UserDto.SignupReq dto) {
        UserDto.SignupRes result =  userService.signup(dto);

        return ResponseEntity.ok(result);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto.LoginReq dto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(), null);

        Authentication authentication = authenticationManager.authenticate(token);
        AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();

        if(user != null) {
            String jwt = jwtUtil.createToken(user.getIdx(), user.getUsername(), user.getRole());
            return ResponseEntity.ok().header("Set-Cookie", "ATOKEN=" + jwt + "; Path=/").build();
        }

        return ResponseEntity.ok("로그인 실패");
    }

    @GetMapping("/verify")
    public ResponseEntity verify(String uuid) {
        userService.verify(uuid);
        // 인증 성공하면 프론트로 리다이렉트
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create("http://localhost:5173")).build();
    }
}
