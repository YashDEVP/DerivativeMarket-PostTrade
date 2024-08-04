package com.derivativemarket.posttrade.org.controllers;


import com.derivativemarket.posttrade.org.dto.SignUpDTO;
import com.derivativemarket.posttrade.org.dto.CompanyDTO;
import com.derivativemarket.posttrade.org.services.CompanyService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CompanyService companyService;

    @PostMapping("/signup")
    public ResponseEntity<CompanyDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        CompanyDTO companyDTO=companyService.signUp(signUpDTO);
        return ResponseEntity.ok(companyDTO);
    }
}
