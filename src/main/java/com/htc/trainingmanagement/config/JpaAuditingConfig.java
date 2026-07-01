package com.htc.trainingmanagement.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing // Enables JPA auditing (@CreatedBy, @LastModifiedBy)
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware() {

        return () -> {
            // Get the currently logged-in user
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            // If no user is logged in, use SYSTEM as the auditor
            if (authentication == null
                    || !authentication.isAuthenticated()
                    || "anonymousUser".equals(authentication.getName())) {

                return Optional.of("SYSTEM");
            }

            // Store the logged-in user's email/username
            return Optional.of(authentication.getName());
        };
    }
}