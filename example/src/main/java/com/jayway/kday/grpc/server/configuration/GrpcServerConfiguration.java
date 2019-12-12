package com.jayway.kday.grpc.server.configuration;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Creates a server (io.grpc.Server) with port 8074 opened if the property 'grpc.enabled' equals
 * 'true' (Env Var 'GRPC_ENABLED' overrides this property.) and a BindableService implementation
 * bean exists, i.e. the server-side implementation of a gRPC client-server setup.
 */
@Component
@ConditionalOnProperty(value = "grpc.enabled", havingValue = "true", matchIfMissing = true)
public class GrpcServerConfiguration {

    @Value("${grpc.port}")
    private int port;

    // The gRPC service implementation bean provided by the actual microservice that uses this core
    // module.
    @Autowired
    private BindableService bindableService;

    private Server server;

    public GrpcServerConfiguration() {
        // Default constructor
    }

    /**
     * When this class is beanified by Spring on startup, this method is executed which starts the
     * gRPC server. Requires the BindableService to be autowired, hence the PostConstruct annotation.
     * Interceptors are added and, on an incoming call, executed in the order th
     *
     * @throws IOException Exception thrown by the server during startup.
     */
    @PostConstruct
    public void startServer() throws IOException, InterruptedException {
        System.out.println("Building gRPC server..");

        // Setup and initialize gRPC server tracing and interceptors.
        this.server =
                ServerBuilder
                        .forPort(this.port)
                        .addService(
                                ServerInterceptors.intercept(bindableService))
                        .build();

        server.start();
        System.out.println("gRPC server started.");
        server.awaitTermination();
    }
}
