package com.derivativemarket.posttrade.org.dto;

import lombok.Data;

@Data
public class SignUpDTO {

    private String companyEmail;

    private String password;

    private String companyShortName;

    private String companyName;
}
