This is a gRPC application.
It has 3 modules:
    1. common: has proto file
    2. client: has client code
    3. server: has grpc server

Start the gRPC server in server module.
Then run the client code.

===============
If you have to use 3rd party gRPC application:
1. 3rd Party will provide you the .proto files, server name and port on which their services are running.
2. Put the proto file in a common module and generate the Stubs
3. Write a client code to call the Service.

========== Struct =========
In client module there is 3 example to contruct a Struct object.
It also has a Utility to convert JSON string to Struct.
