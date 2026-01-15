package com.accessflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * AccessFlow - Backend SaaS for Authentication, Authorization and Audit Logging
 *
 * Main application class for the AccessFlow system.
 * This application provides production-grade authentication, fine-grained authorization (RBAC),
 * multi-tenant organizations, and security audit logging.
 */
@SpringBootApplication
@EnableJpaAuditing
public class AccessFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessFlowApplication.class, args);
    }

}
