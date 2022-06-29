package com.dislinkt.connectionservice.service;

import com.dislinkt.connectionservice.enums.ConnectionState;
import com.dislinkt.connectionservice.model.Connection;
import com.dislinkt.connectionservice.repository.ConnectionRepository;
import com.dislinkt.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableMongoRepositories("com.dislinkt.connectionservice.repository")
@GrpcService
public class ConnectionService extends ConnectionServiceGrpc.ConnectionServiceImplBase  {
    @Autowired
    private ConnectionRepository connectionRepository;

    @Override
    public void createConnection(CreateConnection request, StreamObserver<CreateConnectionResponse> responseObserver) {

        connectionRepository.save(new Connection(2, request.getSender(), request.getReceiver(), request.getState()));
        CreateConnectionResponse res = CreateConnectionResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void getConnections(GetConnections request, StreamObserver<GetConnectionsResponse> responseObserver) {
        //super.getConnections(request, responseObserver);
        GetConnectionsResponse.Builder res = GetConnectionsResponse.newBuilder();
        for(Connection c : connectionRepository.findAll()){
            if((c.getReceiver() == request.getUserId() || c.getSender() == request.getUserId()) && c.getConnectionState() == ConnectionState.CONNECTED){
                res.addConnections(ConnectionEntity.newBuilder()
                        .setId(c.getId())
                        .setSender(c.getSender())
                        .setReceiver(c.getReceiver())
                        .setState(c.getConnectionStateString())
                        .build()
                );
            }
        }
        System.out.println(res.getConnectionsCount());
        responseObserver.onNext(res.build());
        responseObserver.onCompleted();
    }
}
