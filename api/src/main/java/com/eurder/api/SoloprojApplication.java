package com.eurder.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.eurder")
public class SoloprojApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoloprojApplication.class, args);
    }

}
