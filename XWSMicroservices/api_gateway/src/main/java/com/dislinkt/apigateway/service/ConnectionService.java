package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.ConnectionDTO;
import com.dislinkt.apigateway.dto.SearchProfileResponseDTO;
import com.dislinkt.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        ConnectionResponse res = authStub.createConnection(req);
        if (res.getSuccess())
            return true;
        return  false;
    }

    public List<ConnectionDTO> getConnections(long userId){
        ArrayList<ConnectionDTO> retList = new ArrayList<ConnectionDTO>();
        GetConnections req = GetConnections.newBuilder()
                .setUserId(userId)
                .build();
        GetConnectionsResponse response = authStub.getConnections(req);
        return convertToGetConnectionsResponseDTO(response);
    }

    public Boolean deleteConnection(long connectionId){
        ConnectionId req = ConnectionId.newBuilder().setConnectionId(connectionId).build();
        ConnectionResponse response = authStub.deleteConnection(req);
        if (response.getSuccess())
            return true;
        return  false;
    }

    public Boolean updateConnection(ConnectionDTO connection){
        ConnectionEntity req = ConnectionEntity.newBuilder()
                .setId(connection.id)
                .setSender(connection.sender)
                .setReceiver(connection.receiver)
                .setState(connection.connectionState)
                .build();
        ConnectionResponse response = authStub.updateConnection(req);
        if (response.getSuccess())
            return true;
        return  false;
    }


    public List<ConnectionDTO> convertToGetConnectionsResponseDTO(GetConnectionsResponse response) {
        List<ConnectionDTO> retList = new ArrayList<ConnectionDTO>();
        for (int i = 0; i < response.getConnectionsCount(); i++) {
            ConnectionEntity res = response.getConnections(i);
            retList.add(new ConnectionDTO(res.getId(), res.getSender(), res.getReceiver(), res.getState()));
        }
        return retList;
    }

}
