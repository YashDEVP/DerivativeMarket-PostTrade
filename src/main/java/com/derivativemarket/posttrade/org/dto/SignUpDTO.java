package com.derivativemarket.posttrade.org.dto;

import com.derivativemarket.posttrade.org.entities.enums.Permission;
import com.derivativemarket.posttrade.org.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

    private String companyEmail;

    private String password;

    private String companyShortName;

    private String companyName;

    private Set<Role> roles;

    private Set<Permission> permissions;
}
