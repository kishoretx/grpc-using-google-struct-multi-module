package com.example.client;

import com.example.grpc.common.HelloRequest;
import com.example.grpc.common.HelloResponse;
import com.example.grpc.common.HelloWorldGrpc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldClient_2 {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();



        // Prepare the request message
        Map<String, Value> fieldsMap = new HashMap<>();
        fieldsMap.put("streetName", Value.newBuilder().setStringValue("2938 Stokely Hill").build());
        fieldsMap.put("city", Value.newBuilder().setStringValue("San Antonio").build());
        fieldsMap.put("state", Value.newBuilder().setStringValue("TX").build());
        fieldsMap.put("zip", Value.newBuilder().setNumberValue(78258).build());
        Struct structData = Struct.newBuilder().putAllFields(fieldsMap).build();

        HelloRequest request = HelloRequest.newBuilder()
                .setName("Kishore Kumar")
                .setData(structData)
                .build();

        // Call the service method using the stub
        HelloWorldGrpc.HelloWorldBlockingStub stub = HelloWorldGrpc.newBlockingStub(channel);
        HelloResponse response = stub.sayHello(request);

        System.out.println("Response from servers: " + response.toString());

        channel.shutdown();
    }

}
