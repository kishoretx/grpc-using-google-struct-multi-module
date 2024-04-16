package com.example.client;

import com.example.grpc.common.HelloRequest;
import com.example.grpc.common.HelloResponse;
import com.example.grpc.common.HelloWorldGrpc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.HashMap;
import java.util.Map;

/*
 * Here we are taking a JSON string and converting to Struct.
 *
 */
public class HelloWorldClient_3 {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        String jsonString = "{" +
                "\"name\": \"John Doe\"," +
                "\"age\": 35," +
                "\"email\": \"john.doe@example.com\"," +
                "\"address\": {" +
                "\"street\": \"123 Main St\"," +
                "\"city\": \"New York\"," +
                "\"state\": \"NY\"," +
                "\"zip\": \"10001\"" +
                "}," +
                "\"phone_numbers\": [" +
                "{" +
                "\"type\": \"home\"," +
                "\"number\": \"555-1234\"" +
                "}," +
                "{" +
                "\"type\": \"work\"," +
                "\"number\": \"555-5678\"" +
                "}," +
                "{" +
                "\"type\": \"mobile\"," +
                "\"number\": \"555-9012\"" +
                "}" +
                "]," +
                "\"tags\": [\"tag1\", \"tag2\", \"tag3\", \"tag4\", \"tag5\"]," +
                "\"friends\": [" +
                "{" +
                "\"name\": \"Alice\"," +
                "\"age\": 30," +
                "\"email\": \"alice@example.com\"" +
                "}," +
                "{" +
                "\"name\": \"Bob\"," +
                "\"age\": 40," +
                "\"email\": \"bob@example.com\"" +
                "}," +
                "{" +
                "\"name\": \"Charlie\"," +
                "\"age\": 25," +
                "\"email\": \"charlie@example.com\"" +
                "}" +
                "]," +
                "\"preferences\": {" +
                "\"theme\": \"dark\"," +
                "\"language\": \"english\"," +
                "\"notifications\": true" +
                "}" +
                "}";


        Struct struct = Utility_JsonToStructConverter.convertJsonToStruct(jsonString);

        //Example #2
        HelloRequest request = HelloRequest.newBuilder()
                .setName("Manoj Kumar")
                .setData(struct)
                .build();

        // Call the service method using the stub
        HelloWorldGrpc.HelloWorldBlockingStub stub = HelloWorldGrpc.newBlockingStub(channel);
        HelloResponse response = stub.sayHello(request);

        System.out.println("Response from servers: " + response.toString());

        channel.shutdown();
    }

    public static Struct myData() {
        // Create inner Struct for address
        Struct.Builder addressBuilder = Struct.newBuilder();
        addressBuilder.putFields("city", Value.newBuilder().setStringValue("Berlin").build());
        addressBuilder.putFields("country", Value.newBuilder().setStringValue("Germany").build());
        Struct addressStruct = addressBuilder.build();

        // Create inner Struct for contact
        Struct.Builder contactBuilder = Struct.newBuilder();
        contactBuilder.putFields("phone", Value.newBuilder().setStringValue("987-654-3210").build());
        contactBuilder.putFields("email", Value.newBuilder().setStringValue("john123@xyz.com").build());
        contactBuilder.putFields("age", Value.newBuilder().setNumberValue(37).build());
        contactBuilder.putFields("human", Value.newBuilder().setBoolValue(true).build());
        contactBuilder.putFields("address", Value.newBuilder().setStructValue(addressStruct).build());
        Struct contactStruct = contactBuilder.build();

        // Create outer Struct for name and contact
        Struct.Builder outerBuilder = Struct.newBuilder();
        outerBuilder.putFields("name", Value.newBuilder().setStringValue("John").build());
        outerBuilder.putFields("contact", Value.newBuilder().setStructValue(contactStruct).build());
        Struct outerStruct = outerBuilder.build();

        // Print the resulting Struct
        System.out.println("Resulting Struct:");
        System.out.println(outerStruct);
        System.out.println("------------------");

        // Convert OuterStruct to JSON using Gson
        // Create Gson instance with exclusion of reference fields
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT)
                .registerTypeAdapter(Struct.class, new ProtobufStructAdapter())
                .create();

        String json = gson.toJson(outerStruct);
        System.out.println(json);
        System.out.println("============");

        return outerStruct;
    }
}
