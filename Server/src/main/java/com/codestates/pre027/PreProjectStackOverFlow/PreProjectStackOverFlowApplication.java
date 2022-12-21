package com.codestates.pre027.PreProjectStackOverFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PreProjectStackOverFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreProjectStackOverFlowApplication.class, args);
    }

}
