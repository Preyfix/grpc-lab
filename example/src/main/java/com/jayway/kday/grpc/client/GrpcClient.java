package com.jayway.kday.grpc.client;

import com.jayway.kday.grpc.ClueOne;
import com.jayway.kday.grpc.Ping;
import com.jayway.kday.grpc.Pong;
import com.jayway.kday.grpc.MyServiceGrpc;
import com.jayway.kday.grpc.PuzzleGrpc;
import com.jayway.kday.grpc.YourName;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class GrpcClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcClient.class);

    /* BlockingStubs makes synchronous calls.
    The proto file can be modified to support asynchronous calls. */
    private final PuzzleGrpc.PuzzleBlockingStub blockingStub;
    private final ManagedChannel channel;

    /* The client's target server. */
    private String target = "grpc-puzzle-pvufxpciqa-lz.a.run.app";
    private int port = 443;

    private GrpcClient() {
        LOGGER.info("Creating client with target: " + target + ":" + port);
        channel = NettyChannelBuilder.forAddress(target, port).build();
//        blockingStub = MyServiceGrpc.newBlockingStub(channel);
        blockingStub = PuzzleGrpc.newBlockingStub(channel);
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        GrpcClient client = new GrpcClient();
        try {
            // Assembles an RPC call.
            YourName yourName = YourName.newBuilder().setYourName("<insert-name>").build();
            ClueOne clueOne = client.blockingStub.startHere(yourName);
            System.out.println(clueOne.getClue());
        } finally {
            client.shutdown();
        }
    }

}
