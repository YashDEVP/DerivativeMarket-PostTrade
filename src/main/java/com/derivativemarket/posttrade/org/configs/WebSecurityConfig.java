package com.derivativemarket.posttrade.org.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /*formLogin(Customizer.withDefaults() this will give default login page that is provided by spring security */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/trades","/error","/auth/**").permitAll() // this will exclude this url from authenticate
                        .requestMatchers("/trades/**").hasAnyRole("Developer","QA","BA") //specific user any perform this api
                        .anyRequest().authenticated()) //this will authenticate all the request.
                .csrf(csrfConfig -> csrfConfig.disable()) //to disable CSRF Token
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  /*  ALWAYS, create session and use that session
    NEVER,create session and never use that session
    IF_REQUIRED, not create session but you can use the session
    STATELESS  neither create a session nor use that session;
    */
               // .formLogin(Customizer.withDefaults());  we are not creating a session that's wh we are remove login page in future we will use JWT token
        return httpSecurity.build();
    }

    //we have created signup flow with jwt token so  i have commented this becoz we need two user
    /*
    @Bean
    UserDetailsService myInMemoryUserDetailsService(){
        UserDetails developer= User.withUsername("Hemant ")
                .password(passwordEncoder().encode("Hemant@123"))
                .roles("Developer")
                .build();

        UserDetails fundManager= User.withUsername("ABC Capital")
                .password(passwordEncoder().encode("Capital@123"))
                .roles("FundManager")
                .build();

        UserDetails bA= User.withUsername("Rooshad Joshi")
                .password(passwordEncoder().encode("Joshi@123"))
                .roles("BA")
                .build();
        return new InMemoryUserDetailsManager(developer,fundManager,bA);

    }
    */
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
