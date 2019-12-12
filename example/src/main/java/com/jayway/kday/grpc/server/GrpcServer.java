package com.jayway.kday.grpc.server;

import org.springframework.boot.SpringApplication;

@org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = "com.jayway.kday.grpc")
public class GrpcServer {
    public static void main(String[] args) {
        SpringApplication.run(GrpcServer.class, args);
    }
}
