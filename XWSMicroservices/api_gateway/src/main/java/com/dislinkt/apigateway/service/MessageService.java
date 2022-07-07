package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.GetMessagesDTO;
import com.dislinkt.apigateway.dto.MessageDTO;
import com.dislinkt.apigateway.dto.NewMessageDTO;
import com.dislinkt.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @GrpcClient("message-grpc-service")
    MessageServiceGrpc.MessageServiceBlockingStub messageStub;

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
        if (res.getSuccess())
            return true;
        return false;
    }

    private List<MessageDTO> convertMessagesToDTO(GetMessagesResponse res) {
        List <MessageDTO> messages = new ArrayList<>();
        for(MessageResponse m : res.getMessagesList()){
            messages.add(new MessageDTO(m.getSenderId(), m.getReceiverId(), m.getText(), m.getDateCreated()));
        }
        return messages;
    }

}
