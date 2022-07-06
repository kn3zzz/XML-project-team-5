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
import java.util.NoSuchElementException;
import java.util.SimpleTimeZone;

@EnableMongoRepositories("com.dislinkt.connectionservice.repository")
@GrpcService
public class ConnectionService extends ConnectionServiceGrpc.ConnectionServiceImplBase  {
    @Autowired
    private ConnectionRepository connectionRepository;

    @Override
    public void createConnection(CreateConnection request, StreamObserver<ConnectionResponse> responseObserver) {

        connectionRepository.save(new Connection(this.generateConnectionId(), request.getSender(), request.getReceiver(), request.getState()));
        ConnectionResponse res = ConnectionResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void getConnections(GetConnections request, StreamObserver<GetConnectionsResponse> responseObserver) {
        //super.getConnections(request, responseObserver);
        GetConnectionsResponse.Builder res = GetConnectionsResponse.newBuilder();
        for(Connection c : connectionRepository.findAll()){
            if((c.getReceiver() == request.getUserId() || c.getSender() == request.getUserId()) && c.getConnectionState() != ConnectionState.IDLE){
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

    @Override
    public void deleteConnection(ConnectionId request, StreamObserver<ConnectionResponse> responseObserver) {

        Connection entity = connectionRepository.findById(request.getConnectionId()).get();
        connectionRepository.delete(entity);
        ConnectionResponse res = ConnectionResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void updateConnection(ConnectionEntity request, StreamObserver<ConnectionResponse> responseObserver) {
        ConnectionResponse res;
        try {
            Connection c = connectionRepository.findById(request.getId()).get();
            System.out.println(request.getState());
            c.setConnectionStateString(request.getState());
            c.setSender(request.getSender());
            c.setReceiver(request.getReceiver());

            connectionRepository.save(c);
            System.out.println(c);

        } catch (Exception e){
            res = ConnectionResponse.newBuilder().setMessage("Failed").setSuccess(false).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }
        res = ConnectionResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
    private long generateConnectionId() {
        for (long i = 1; i < 1000000000; i++) {
            try {
                connectionRepository.findById(i).get();
            } catch (NoSuchElementException e){
                return i;
            }
        }
        return 0;
    }

}

