package com.derivativemarket.posttrade.org.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class CompanyDTO {

    private Long Id;
    private String companyEmail;

    private String companyShortName;

    private String companyName;
}
