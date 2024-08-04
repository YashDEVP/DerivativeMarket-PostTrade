package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretkey;

    private SecretKey getSecretkey(){
        return Keys.hmacShaKeyFor(jwtSecretkey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(CompanyEntity companyEntity){
        return Jwts.builder()
                .subject(companyEntity.getId().toString())
                .claim("emial",companyEntity.getCompanyEmail())
                .claim("roles", Set.of("FundManager","Developer"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(getSecretkey())
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims=Jwts.parser()
                .verifyWith(getSecretkey())
                .build()
                .parseSignedClaims(token)//verify e token  valid or not
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }
}
