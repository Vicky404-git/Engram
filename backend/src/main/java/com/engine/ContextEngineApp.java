package com.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // Required for the background file-watcher daemon
public class ContextEngineApp {
    public static void main(String[] args) {
        SpringApplication.run(ContextEngineApp.class, args);
    }
}
