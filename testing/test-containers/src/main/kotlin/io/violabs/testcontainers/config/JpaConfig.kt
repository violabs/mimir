package io.violabs.testcontainers.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = ["io.violabs.testcontainers.repository"])
@EntityScan(basePackages = ["io.violabs.testcontainers.entity"])
@EnableTransactionManagement
class JpaConfig