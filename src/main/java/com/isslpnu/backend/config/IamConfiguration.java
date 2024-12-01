package com.isslpnu.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan({"com.isslpnu.backend.security"})
@EnableScheduling
@EnableAsync
@EnableJpaAuditing(dateTimeProviderRef = "localDateTimeAuditProvider")
public class IamConfiguration {



}
