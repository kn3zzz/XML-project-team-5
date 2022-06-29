package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.ConnectionDTO;
import com.dislinkt.grpc.ConnectionServiceGrpc;
import com.dislinkt.grpc.CreateConnection;
import com.dislinkt.grpc.CreateConnectionResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    @GrpcClient("connection-grpc-service")
    ConnectionServiceGrpc.ConnectionServiceBlockingStub authStub;

    public Boolean createConnection(ConnectionDTO connection){
        CreateConnection req = CreateConnection.newBuilder()
                .setSender(connection.sender)
                .setReceiver(connection.receiver)
                .setState(connection.connectionState)
                .build();
        CreateConnectionResponse res = authStub.createConnection(req);
        if (res.getSuccess())
            return true;
        return  false;
    }
}
