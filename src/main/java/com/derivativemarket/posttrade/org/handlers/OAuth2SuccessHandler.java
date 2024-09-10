package com.derivativemarket.posttrade.org.handlers;

import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import com.derivativemarket.posttrade.org.filters.JwtAuthFilter;
import com.derivativemarket.posttrade.org.services.CompanyService;
import com.derivativemarket.posttrade.org.services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Value("${deploy.env}")
    private String deployEnv;

    private final CompanyService companyService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
        OAuth2AuthenticationToken token =(OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User=(DefaultOAuth2User) token.getPrincipal();
        log.info(oAuth2User.getAttribute("email"));
        String email=oAuth2User.getAttribute("email");
        CompanyEntity company=companyService.getCompanyByEmail(email);
        if(company==null){
            CompanyEntity newCompany=company.builder()
                    .companyName(oAuth2User.getAttribute("name"))
                    .companyEmail(email)
                    .build();
            company=companyService.save(newCompany);

        }
        String accessToken=jwtService.generateAccessToken(company);
        String refreshToken=jwtService.generateRefreshToken(company);
        Cookie cookie=new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);//noone can access it onlybe access by http
        cookie.setSecure("production".equals(deployEnv)); // noone can update the cookie in middle we have to only use have to only use when we are in production env
        response.addCookie(cookie);

        String fromEndUrl="http://localhost:8080/home.html?token="+accessToken;
        //getRedirectStrategy().sendRedirect(request,response,fromEndUrl);
        response.sendRedirect(fromEndUrl);
    }
}

