package com.demisco.digishop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author m.zarabi
 * @author h.zare Working on demo security
 */
@SpringBootApplication
@EnableCaching
public class DigishopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigishopApplication.class, args);
    }
}
