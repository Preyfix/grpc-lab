package com.jayway.kday.grpc.client;

import com.jayway.kday.grpc.Ping;
import com.jayway.kday.grpc.Pong;
import com.jayway.kday.grpc.MyServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class GrpcClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcClient.class);

    /* BlockingStubs makes synchronous calls.
    The proto file can be modified to support asynchronous calls. */
    private final MyServiceGrpc.MyServiceBlockingStub blockingStub;
    private final ManagedChannel channel;

    /* The client's target server. */
    private String target = "localhost";
    private int port = 8074;

    private void ping(Ping request) {
        // Sends an assembled request and awaits response.
        Pong response = blockingStub.ping(request);
        LOGGER.info("Response received: " + response.getAnswer());
    }

    private GrpcClient() {
        LOGGER.info("Creating client with target: " + target + ":" + port);
        channel = NettyChannelBuilder.forAddress(target, port).usePlaintext().build();
        blockingStub = MyServiceGrpc.newBlockingStub(channel);
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        GrpcClient client = new GrpcClient();
        try {
            // Assembles an RPC call.
            Ping ping = Ping.newBuilder().setText("Ping!").build();
            client.ping(ping);
        } finally {
            client.shutdown();
        }
    }
}
