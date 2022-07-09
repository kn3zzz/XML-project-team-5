package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.ConnectionDTO;
import com.dislinkt.apigateway.dto.SearchProfileResponseDTO;
import com.dislinkt.apigateway.dto.UserInfoChangeDTO;
import com.dislinkt.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionService {
    @GrpcClient("connection-grpc-service")
    ConnectionServiceGrpc.ConnectionServiceBlockingStub authStub;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    NotificationService notificationService;

    public Boolean createConnection(ConnectionDTO connection){
        CreateConnection req = CreateConnection.newBuilder()
                .setSender(connection.sender)
                .setReceiver(connection.receiver)
                .setState(connection.connectionState)
                .build();
        ConnectionResponse res = authStub.createConnection(req);
        String notificationText = "";
        if (res.getSuccess()) {
            UserInfoChangeDTO user = authenticationService.getUser(connection.sender);
            List<UserID> ids = new ArrayList<>();
            ids.add(UserID.newBuilder().setId(connection.receiver).build());
            ConnectedUsers usersFinal = authenticationService.authStub.getUsersWithNotificationOn(ConnectedUsers.newBuilder().addAllUserIds(ids).build());
            if (res.getState().equalsIgnoreCase("CONNECTED"))
                notificationText = user.getUsername() + " - " + user.getName() + " " + user.getLastname() + " just connected with you !";
            else if (res.getState().equalsIgnoreCase("PENDING"))
                notificationText = user.getUsername() + " - " + user.getName() + " " + user.getLastname() + " just sent you a connection request !";
            notificationService.sendNotification(convertToLongId(usersFinal), notificationText);
            return true;
        }
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
        ConnectionResponse res = authStub.updateConnection(req);
        String notificationText = "";
        if (res.getSuccess() && res.getState().equalsIgnoreCase("CONNECTED")) {
            UserInfoChangeDTO user = authenticationService.getUser(connection.receiver);
            List<UserID> ids = new ArrayList<>();
            ids.add(UserID.newBuilder().setId(connection.sender).build());
            ConnectedUsers usersFinal = authenticationService.authStub.getUsersWithNotificationOn(ConnectedUsers.newBuilder().addAllUserIds(ids).build());
            notificationText = user.getUsername() + " - " + user.getName() + " " + user.getLastname() + " just connected with you !";
            notificationService.sendNotification(convertToLongId(usersFinal), notificationText);
            return true;
        }
        if (res.getSuccess())
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

    private List<Long> convertToLongId(ConnectedUsers users){
        List <Long> ids = new ArrayList<>();
        for (UserID id : users.getUserIdsList()){
            ids.add(id.getId());
        }
        return ids;
    }

}
