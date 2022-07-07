package com.dislinkt.messageservice.service;


import com.dislinkt.grpc.*;
import com.dislinkt.messageservice.model.Chat;
import com.dislinkt.messageservice.model.Message;
import com.dislinkt.messageservice.repository.MessageRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class MessageService extends MessageServiceGrpc.MessageServiceImplBase {

    @Autowired
    MessageRepository messageRepository;
    @Override
    public void sendMessage(NewMessage request, StreamObserver<NewMessageResponse> responseObserver) {
        Chat c = new Chat();
        String chatId = "";
        try {
            if (request.getSenderId() < request.getReceiverId()) {
                chatId = request.getSenderId() + "-" + request.getReceiverId();
                c = messageRepository.findById(request.getSenderId() + "-" + request.getReceiverId()).get();
            } else {
                chatId = request.getReceiverId() + "-" + request.getSenderId();
                c = messageRepository.findById(request.getReceiverId() + "-" + request.getSenderId()).get();
            }
            List <Message> messages = c.getMessages();
            messages.add(new Message(request.getSenderId(), request.getReceiverId(), request.getText(), new Date()));
            c.setMessages(messages);
            messageRepository.save(c);
            NewMessageResponse res = NewMessageResponse.newBuilder().setMessage("Success").setSuccess(true).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        } catch (NoSuchElementException e) {
            List <Message> messages = new ArrayList<>();
            messages.add(new Message(request.getSenderId(), request.getReceiverId(), request.getText(), new Date()));
            messageRepository.save(new Chat(chatId, messages));
            NewMessageResponse res = NewMessageResponse.newBuilder().setMessage("Success").setSuccess(true).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getMessages(GetMessages request, StreamObserver<GetMessagesResponse> responseObserver) {
        List<Message> messages = new ArrayList<>();
        List<MessageResponse> responseList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy. hh:mm:ss");
        try {
            if (request.getSenderId() < request.getReceiverId()) {
                messages = messageRepository.findById(request.getSenderId() + "-" + request.getReceiverId()).get().getMessages();
            } else {
                messages = messageRepository.findById(request.getReceiverId() + "-" + request.getSenderId()).get().getMessages();
            }
            for (Message m : messages){
                responseList.add(MessageResponse.newBuilder()
                        .setSenderId(m.getSenderId())
                        .setReceiverId(m.getReceiverId())
                        .setDateCreated(dateFormat.format(m.getDateCreated()))
                        .setText(m.getText())
                        .build());
            }
            GetMessagesResponse res = GetMessagesResponse.newBuilder().addAllMessages(responseList).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        } catch (NoSuchElementException e) {
            GetMessagesResponse res = GetMessagesResponse.newBuilder().addAllMessages(new ArrayList<MessageResponse>()).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        }
    }

}
