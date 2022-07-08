package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.*;
import com.dislinkt.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @GrpcClient("message-grpc-service")
    MessageServiceGrpc.MessageServiceBlockingStub messageStub;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    NotificationService notificationService;


    public List<MessageDTO> getMessages(GetMessagesDTO getMessagesDTO) {
        GetMessages req = GetMessages.newBuilder()
                .setSenderId(getMessagesDTO.senderId)
                .setReceiverId(getMessagesDTO.receiverId)
                .build();
        GetMessagesResponse res = messageStub.getMessages(req);
        return convertMessagesToDTO(res);
    }

    public Boolean sendMessage(NewMessageDTO messageDTO) {
        NewMessage req = NewMessage.newBuilder()
                .setSenderId(messageDTO.senderId)
                .setReceiverId(messageDTO.receiverId)
                .setText(messageDTO.text)
                .build();
        NewMessageResponse res = messageStub.sendMessage(req);
        String notificationText = "";
        if (res.getSuccess()) {
            UserInfoChangeDTO user = authenticationService.getUser(messageDTO.senderId);
            List <UserID> ids = new ArrayList<>();
            ids.add(UserID.newBuilder().setId(messageDTO.receiverId).build());
                ConnectedUsers usersFinal = authenticationService.authStub.getUsersWithNotificationOn(ConnectedUsers.newBuilder().addAllUserIds(ids).build());
                notificationText = user.getUsername() + " - " + user.getName() + " " + user.getLastname() + " just sent you a new message !";
                notificationService.sendNotification(convertToLongId(usersFinal), notificationText);
            return true;
        }
        return false;
    }

    private List<Long> convertToLongId(ConnectedUsers users){
        List <Long> ids = new ArrayList<>();
        for (UserID id : users.getUserIdsList()){
            ids.add(id.getId());
        }
        return ids;
    }

    private List<MessageDTO> convertMessagesToDTO(GetMessagesResponse res) {
        List <MessageDTO> messages = new ArrayList<>();
        for(MessageResponse m : res.getMessagesList()){
            messages.add(new MessageDTO(m.getSenderId(), m.getReceiverId(), m.getText(), m.getDateCreated()));
        }
        return messages;
    }

}
