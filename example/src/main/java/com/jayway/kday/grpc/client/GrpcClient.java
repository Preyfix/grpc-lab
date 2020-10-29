package com.jayway.kday.grpc.client;

import com.jayway.kday.grpc.ClueFour;
import com.jayway.kday.grpc.ClueOne;
import com.jayway.kday.grpc.ClueThree;
import com.jayway.kday.grpc.ClueTwo;
import com.jayway.kday.grpc.FinalSecret;
import com.jayway.kday.grpc.Key;
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
//    private String target = "localhost";
//    private int port = 8074;
    private String target = "grpc-puzzle-pvufxpciqa-lz.a.run.app";
    private int port = 443;

    private void start(YourName request) {
        // Sends an assembled request and awaits response.
        ClueOne response = blockingStub.startHere(request);
        LOGGER.info("Response received: " + response.getClue() + " - " + response.getMessage());
    }

    private GrpcClient() {
        LOGGER.info("Creating client with target: " + target + ":" + port);
        channel = NettyChannelBuilder.forAddress(target, port).build();
        blockingStub = PuzzleGrpc.newBlockingStub(channel);
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        GrpcClient client = new GrpcClient();
        try {
            // Assembles an RPC call.
            YourName yourName = YourName.newBuilder().setYourName("Martin Hjelmqvist").build();
            client.start(yourName);
        } finally {
            client.shutdown();
        }
    }
}
