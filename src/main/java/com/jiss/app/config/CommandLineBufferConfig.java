package com.jiss.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandLineBufferConfig {
    @Bean
    public StringBuilder commandBuffer() {
        return new StringBuilder();
    }
}
