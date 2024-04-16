package com.example.server;

import com.example.grpc.common.HelloRequest;
import com.example.grpc.common.HelloResponse;
import com.example.grpc.common.HelloWorldGrpc;
import com.google.protobuf.Struct;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class HelloWorldService extends HelloWorldGrpc.HelloWorldImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String greeting = "Hello, " + request.getName() + "!";

        Struct struct = request.getData();

        HelloResponse response = HelloResponse.newBuilder().setGreeting(greeting).setData(struct).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
