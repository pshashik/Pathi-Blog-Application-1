package com.pathi.blog.controller;

import com.pathi.blog.payload.JwtResponseDto;
import com.pathi.blog.payload.LoginDto;
import com.pathi.blog.payload.RegisterDto;
import com.pathi.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Value("${jwt.expiry-milliseconds}")
    private Long jwtExpiry;
   private AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setAccessToken(token);
        jwtResponseDto.setJwtExpiryInMs(jwtExpiry);
        return new ResponseEntity<>(jwtResponseDto, HttpStatus.OK);
    }

    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.register(registerDto),HttpStatus.OK);
    }
}
