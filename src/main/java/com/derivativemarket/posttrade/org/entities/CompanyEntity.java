package com.derivativemarket.posttrade.org.entities;

import com.derivativemarket.posttrade.org.entities.enums.Permission;
import com.derivativemarket.posttrade.org.entities.enums.Role;
import com.derivativemarket.posttrade.org.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CompanyEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String companyEmail;

    private String password;

    private String companyShortName;

    private String companyName;


    @ElementCollection(fetch=FetchType.EAGER)// we want to fetch data as soon as possible
    @Enumerated(EnumType.STRING) // EnumType.STRING we do this becoz it store role value as string otherwise we don't do this it store role in form of integer
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthorityForRole(role);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.companyEmail;
    }
}