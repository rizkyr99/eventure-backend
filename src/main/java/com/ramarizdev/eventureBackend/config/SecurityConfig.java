package com.ramarizdev.eventureBackend.config;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log
public class SecurityConfig {
    private final RsaKeyConfigProperties rsaKeyConfigProperties;

    public SecurityConfig(RsaKeyConfigProperties rsaKeyConfigProperties) {
        this.rsaKeyConfigProperties = rsaKeyConfigProperties;
    }
}
