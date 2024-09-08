package com.derivativemarket.posttrade.org.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class LoginDTO {
    String companyEmail;
    String password;

}
