package com.derivativemarket.posttrade.org.controllers;


import com.derivativemarket.posttrade.org.dto.LoginDTO;
import com.derivativemarket.posttrade.org.dto.LoginResponseDTO;
import com.derivativemarket.posttrade.org.dto.SignUpDTO;
import com.derivativemarket.posttrade.org.dto.CompanyDTO;
import com.derivativemarket.posttrade.org.services.AuthService;
import com.derivativemarket.posttrade.org.services.CompanyService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CompanyService companyService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<CompanyDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        CompanyDTO companyDTO=companyService.signUp(signUpDTO);
        return ResponseEntity.ok(companyDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
        LoginResponseDTO token= authService.logIn(loginDTO);
        Cookie cookie=new Cookie("refreshToken",token.getRefreshToken());
        cookie.setHttpOnly(true);//noone can access it onlybe access by http
        cookie.setSecure("production".equals(deployEnv)); // noone can update the cookie in middle we have to only use have to only use when we are in production env
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        String refreshToken =Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst().orElseThrow(()-> new AuthenticationServiceException("Refresh token not found inside the cookie"));
        LoginResponseDTO loginResponseDTO=  authService.refreshToken(refreshToken)  ;
        return ResponseEntity.ok(loginResponseDTO);
    }
}
