package com.jayway.kday.grpc.server.implementation;

import com.jayway.kday.grpc.MyServiceGrpc;
import com.jayway.kday.grpc.Ping;
import com.jayway.kday.grpc.Pong;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

/**
 * A gRPC service implementation. When annotated with @Service (or @Component), this implementation
 * will be beanified and used by dfp-core to start a gRPC server (if the property 'grpc.enabled'
 * or environment variable GRPC_ENABLED is 'true').
 */
@Service
public class GrpcServerImpl extends MyServiceGrpc.MyServiceImplBase {

    /**
     * When called, assemble and respond with a Pong object.
     *
     * @param ping
     * @param responseStreamObserver
     */
    @Override
    public void ping(Ping ping, StreamObserver<Pong> responseStreamObserver) {
        Pong pong = Pong.newBuilder()
                .setAnswer(true)
                .build();
        responseStreamObserver.onNext(pong);
        responseStreamObserver.onCompleted();
    }
}
