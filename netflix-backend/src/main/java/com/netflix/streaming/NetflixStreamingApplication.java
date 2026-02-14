package com.netflix.streaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class NetflixStreamingApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetflixStreamingApplication.class, args);
    }
}
