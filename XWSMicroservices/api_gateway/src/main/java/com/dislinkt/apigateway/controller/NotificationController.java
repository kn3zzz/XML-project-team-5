package com.dislinkt.apigateway.controller;

import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.dto.UpdateNotificationDTO;
import com.dislinkt.apigateway.service.AuthenticationService;
import com.dislinkt.apigateway.service.NotificationService;
import com.dislinkt.grpc.NotificationServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/markAsRead")
    public ResponseEntity<Boolean> markAsRead(@RequestBody UpdateNotificationDTO updateDTO){
        return new ResponseEntity<Boolean>(notificationService.markAsRead(updateDTO), HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteNotificationForUser(@RequestBody UpdateNotificationDTO updateDTO){
        return new ResponseEntity<Boolean>(notificationService.deleteNotificationForUser(updateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getNotifications/{id}")
    public ResponseEntity<?> getUserNotifications(@PathVariable("id") long id){
        return new ResponseEntity<>(notificationService.getUserNotifications(id), HttpStatus.OK);
    }
}
