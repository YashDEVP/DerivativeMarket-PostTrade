package com.derivativemarket.posttrade.org.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //when we want to add spring security follow bellow step
        // get security context
        // get authentication
        //get the principle
        //get the username
        return Optional.of("Yash Gupta");
    }
}
