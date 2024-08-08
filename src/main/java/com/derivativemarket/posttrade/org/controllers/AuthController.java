package com.derivativemarket.posttrade.org.controllers;


import com.derivativemarket.posttrade.org.dto.LoginDTO;
import com.derivativemarket.posttrade.org.dto.SignUpDTO;
import com.derivativemarket.posttrade.org.dto.CompanyDTO;
import com.derivativemarket.posttrade.org.services.AuthService;
import com.derivativemarket.posttrade.org.services.CompanyService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CompanyService companyService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<CompanyDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        CompanyDTO companyDTO=companyService.signUp(signUpDTO);
        return ResponseEntity.ok(companyDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
        String token= authService.logIn(loginDTO);
        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true); //noone can access it onlybe access by http
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }
}
