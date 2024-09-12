package com.derivativemarket.posttrade.org.filters;

import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import com.derivativemarket.posttrade.org.services.CompanyService;
import com.derivativemarket.posttrade.org.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CompanyService companyService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try{
        final String requestTokenHeader=request.getHeader("Authorization");// "Bearer asfdvtcjngdj" this actually industry format for token "Bearer " prefix added before the token
        if(requestTokenHeader==null || !requestTokenHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String token =requestTokenHeader.split("Bearer ")[1];
        Long companyId= jwtService.getUserIdFromToken(token);
        if(companyId!=null && SecurityContextHolder.getContext().getAuthentication()==null
        ) {
            CompanyEntity companyEntity = companyService.getCompanyById(companyId);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    companyEntity, null, companyEntity.getAuthorities()
            );
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
            filterChain.doFilter(request,response);
    }catch(Exception ex){
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }

}
