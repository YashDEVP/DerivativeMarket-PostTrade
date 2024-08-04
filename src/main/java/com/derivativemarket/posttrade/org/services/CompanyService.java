package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import com.derivativemarket.posttrade.org.exception.ResourceNotFoundException;
import com.derivativemarket.posttrade.org.repositories.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
@RequiredArgsConstructor
public class CompanyService implements UserDetailsService {

    private final CompanyRepo companyRepo;
    @Override
    public CompanyEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return companyRepo.findByCompanyEmail(email).orElseThrow(()-> new ResourceNotFoundException("Company with this email : " +email+"not found"));
    }
}