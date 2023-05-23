package com.speechmaru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class SpeechMaruBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpeechMaruBackendApplication.class, args);
    }
}
