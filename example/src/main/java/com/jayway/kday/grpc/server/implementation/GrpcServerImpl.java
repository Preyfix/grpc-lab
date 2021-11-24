package com.jayway.kday.grpc.server.implementation;

import com.jayway.kday.grpc.ClueFour;
import com.jayway.kday.grpc.ClueOne;
import com.jayway.kday.grpc.ClueThree;
import com.jayway.kday.grpc.ClueTwo;
import com.jayway.kday.grpc.FinalSecret;
import com.jayway.kday.grpc.Key;
import com.jayway.kday.grpc.PuzzleGrpc;
import com.jayway.kday.grpc.YourName;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

/**
 * A gRPC service implementation. When annotated with @Service (or @Component), this implementation
 * will be beanified and start a gRPC server (if the property 'grpc.enabled'
 * or environment variable GRPC_ENABLED is 'true').
 */
@Service
public class GrpcServerImpl extends PuzzleGrpc.PuzzleImplBase {

    private final String clue1 = "r****y**y*****ot***";
    private final String clue2 = "**p*a******d*****a*";
    private final String clue3 = "******w**b**e***e**";
    private final String clue4 = "*i*j***a**y**v****m";
    private final String validKey = "ripjaywaybydevoteam";
    private final String secretPinCode = "1338";

    /**
     * When called, receive clue one.
     *
     * @param yourName
     * @param responseStreamObserver
     */
    @Override
    public void startHere(YourName yourName, StreamObserver<ClueOne> responseStreamObserver) {
        String name = "";
        if(yourName == null || "".equals(yourName.getYourName())){
            name = "unnamed human";
        }else{
            name = yourName.getYourName();
        }
        ClueOne clue = ClueOne.newBuilder()
                .setClue(clue1)
                .setMessage(String.format("What does this clue mean? Better continue with next endpoint.. Good luck, %s!", name))
                .build();
        responseStreamObserver.onNext(clue);
        responseStreamObserver.onCompleted();
    }

    /**
     * When called, receive clue two if correct clueOne.
     *
     * @param clueOne
     * @param responseStreamObserver
     */
    @Override
    public void endpointOne(ClueOne clueOne, StreamObserver<ClueTwo> responseStreamObserver) {
        if(clueOne.getClue().equals(clue1)){
            ClueTwo clue = ClueTwo.newBuilder()
                                  .setClue(clue2)
                                  .setMessage("Great! Another obscure clue..")
                                  .build();
            responseStreamObserver.onNext(clue);
        }
        responseStreamObserver.onCompleted();
    }

    /**
     * When called, receive clue three if correct clueTwo.
     *
     * @param clueTwo
     * @param responseStreamObserver
     */
    @Override
    public void endpointTwo(ClueTwo clueTwo, StreamObserver<ClueThree> responseStreamObserver) {
        if(clueTwo.getClue().equals(clue2)){
            ClueThree clue = ClueThree.newBuilder()
                                      .setClue(clue3)
                                      .setMessage("Nearly there! There must be a hefty reward..")
                                      .build();
            responseStreamObserver.onNext(clue);
        }
        responseStreamObserver.onCompleted();
    }

    /**
     * When called, receive clue four if correct clueThree.
     *
     * @param clueThree
     * @param responseStreamObserver
     */
    @Override
    public void endpointThree(ClueThree clueThree, StreamObserver<ClueFour> responseStreamObserver) {
        if(clueThree.getClue().equals(clue3)){
            ClueFour clue = ClueFour.newBuilder()
                                    .setClue(clue4)
                                    .setMessage("All clues are collected! How do they produce the key?")
                                    .build();
            responseStreamObserver.onNext(clue);
        }
        responseStreamObserver.onCompleted();
    }

    /**
     * When called, receive final secret if correct key.
     *
     * @param key
     * @param responseStreamObserver
     */
    @Override
    public void solvePuzzle(Key key, StreamObserver<FinalSecret> responseStreamObserver) {
        if(key.getKey().equals(validKey)){
            FinalSecret secret = FinalSecret.newBuilder()
                                    .setSecretPinCode(secretPinCode)
                                    .build();
            responseStreamObserver.onNext(secret);
        }
        responseStreamObserver.onCompleted();
    }
}
