package com.github.stiangao.pipixia

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@SpringBootApplication
@EnableJpaAuditing
class PipixiaApplication

fun main(args: Array<String>) {
    SpringApplication.run(PipixiaApplication::class.java, *args)
}

@Configuration
class AuditorBean : AuditorAware<Long> {
    override
    fun getCurrentAuditor(): Optional<Long> {
        return Optional.of(-1)
    }
}
