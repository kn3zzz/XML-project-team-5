package com.dislinkt.connectionservice.service;

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
        //super.createConnection(request, responseObserver);

        connectionRepository.save(new Connection(2, request.getSender(), request.getReceiver(), request.getState()));
        CreateConnectionResponse res = CreateConnectionResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }


}
