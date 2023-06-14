package org.loghub.loghub.config

import com.jcraft.jsch.JSch
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun jSch() = JSch()

}