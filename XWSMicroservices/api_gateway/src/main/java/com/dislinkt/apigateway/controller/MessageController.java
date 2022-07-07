package com.dislinkt.apigateway.controller;

import com.dislinkt.apigateway.dto.GetMessagesDTO;
import com.dislinkt.apigateway.dto.NewMessageDTO;
import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.service.AuthenticationService;
import com.dislinkt.apigateway.service.MessageService;
import com.dislinkt.grpc.GetMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/getMessages")
    public ResponseEntity<?> getMessages(@RequestBody GetMessagesDTO getMessagesDTO){
        return new ResponseEntity<>(messageService.getMessages(getMessagesDTO), HttpStatus.OK);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<Boolean> sendMessage(@RequestBody NewMessageDTO messageDTO){
        return new ResponseEntity<Boolean>(messageService.sendMessage(messageDTO), HttpStatus.CREATED);
    }
}
