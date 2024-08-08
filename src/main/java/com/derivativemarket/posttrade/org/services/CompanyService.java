package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.dto.CompanyDTO;
import com.derivativemarket.posttrade.org.dto.SignUpDTO;
import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import com.derivativemarket.posttrade.org.exception.ResourceNotFoundException;
import com.derivativemarket.posttrade.org.repositories.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService implements UserDetailsService {

    private final CompanyRepo companyRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public CompanyEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return companyRepo.findByCompanyEmail(email).orElseThrow(()-> new ResourceNotFoundException("Company with this email : " +email+"not found"));
    }

    public CompanyDTO signUp(SignUpDTO signUpDTO) {

       Optional<CompanyEntity> companyEntity= companyRepo.findByCompanyEmail(signUpDTO.getCompanyEmail());
       if(companyEntity.isPresent()){
           throw  new BadCredentialsException("Company with email already exits "+signUpDTO.getCompanyEmail());
       }
       CompanyEntity newCompany= modelMapper.map(signUpDTO, CompanyEntity.class);
       newCompany.setPassword(passwordEncoder.encode(newCompany.getPassword()));
        CompanyEntity savedCompany=companyRepo.save(newCompany);
        System.out.println("savedCompany"+savedCompany.toString());
        //CompanyDTO companyDTO=modelMapper.map(savedCompany,CompanyDTO.class);
        CompanyDTO companyDTO=new CompanyDTO();
        companyDTO.setCompanyEmail(savedCompany.getCompanyEmail());
        companyDTO.setId(savedCompany.getId());
        companyDTO.setCompanyName(savedCompany.getCompanyName());
        companyDTO.setCompanyShortName(savedCompany.getCompanyShortName());
        System.out.println("companyDTO"+companyDTO.toString());
        System.out.println("savedCompany"+savedCompany.toString());
        return companyDTO;
    }
}