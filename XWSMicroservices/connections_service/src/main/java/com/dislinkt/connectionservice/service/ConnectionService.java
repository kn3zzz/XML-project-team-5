package com.dislinkt.connectionservice.service;

import com.dislinkt.connectionservice.enums.ConnectionState;
import com.dislinkt.connectionservice.model.Connection;
import com.dislinkt.connectionservice.model.ConnectionGraph;
import com.dislinkt.connectionservice.model.User;
import com.dislinkt.connectionservice.repository.ConnectionGraphRepository;
import com.dislinkt.connectionservice.repository.ConnectionRepository;
import com.dislinkt.connectionservice.repository.UserGraphRepository;
import com.dislinkt.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


import java.text.SimpleDateFormat;
import java.util.*;

@EnableMongoRepositories("com.dislinkt.connectionservice.repository")
@EnableNeo4jRepositories("com.dislinkt.connectionservice.repository")
@GrpcService
public class ConnectionService extends ConnectionServiceGrpc.ConnectionServiceImplBase  {
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private ConnectionGraphRepository connectionGraphRepository;
    @Autowired
    private UserGraphRepository userGraphRepository;

    @Override
    public void createConnection(CreateConnection request, StreamObserver<ConnectionResponse> responseObserver) {
        connectionRepository.save(new Connection(this.generateConnectionId(),request.getSender() ,request.getReceiver(), request.getState()));
        ConnectionResponse res = ConnectionResponse.newBuilder().setMessage("Success").setSuccess(true).setState(request.getState()).build();
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
        ConnectionResponse res = ConnectionResponse.newBuilder().setMessage("Success").setSuccess(true).setState(entity.getConnectionStateString()).build();
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
            res = ConnectionResponse.newBuilder().setMessage("Failed").setSuccess(false).setState(request.getState()).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }
        res = ConnectionResponse.newBuilder().setMessage("Success").setSuccess(true).setState(request.getState()).build();
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

    @Override
    public void getRecommendations(ConnectionRecommendation request, StreamObserver<ConnectionRecommendationResponse> responseObserver) {
        List<Connection> connections = connectionRepository.findAll();
        if(connectionGraphRepository.count()>0)
            connectionGraphRepository.deleteAll();
        if(userGraphRepository.count()>0)
            userGraphRepository.deleteAll();

        User user1 = new User(1,null);
        User user2 = new User(2,null);
        User user3 = new User(3,null);


        userGraphRepository.save(user1);
        userGraphRepository.save(user2);
        userGraphRepository.save(user3);

        for(Connection c : connections){
            System.out.println(c.getSender() + c.getReceiver() + c.getConnectionStateString());
            connectionGraphRepository.saveConnection(c.getSender(),c.getReceiver(),c.getConnectionStateString());
        }

        List<Long> recommendationIds = new ArrayList<Long>();
        System.out.println(request.getUserID());
        List<User> users =  connectionGraphRepository.findSecondLevelConnections(request.getUserID());
       // users.add(new User(3,null))
        System.out.println(users.size());
        for(User user : users){
            System.out.println(user + "braaaco");
            if(connectionGraphRepository.getConnection(request.getUserID(), user.getId()) == null)
                recommendationIds.add(user.getId());
        }
        ConnectionRecommendationResponse res = ConnectionRecommendationResponse.newBuilder()
                .addAllUserIds(recommendationIds)
                .build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}

