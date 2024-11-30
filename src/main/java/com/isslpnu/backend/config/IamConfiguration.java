package com.isslpnu.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan({"com.isslpnu.backend.security"})
@EnableScheduling
@EnableJpaAuditing(dateTimeProviderRef = "localDateTimeAuditProvider")
public class IamConfiguration {



}
