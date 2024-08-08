package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.dto.LoginDTO;
import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private  final AuthenticationManager authenticationManager;
    private  final JwtService jwtService;

    public String logIn(LoginDTO loginDTO) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getCompanyEmail(),loginDTO.getPassword())
        );
        CompanyEntity companyEntity=(CompanyEntity) authentication.getPrincipal();
        String token=jwtService.generateToken(companyEntity);
        return token;
    }
}
