package com.derivativemarket.posttrade.org.configs;

import com.derivativemarket.posttrade.org.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl") /*now jpa is attached to auditor class*/
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public AuditorAware<String> getAuditorAwareImpl(){return new AuditorAwareImpl();
    }
}
