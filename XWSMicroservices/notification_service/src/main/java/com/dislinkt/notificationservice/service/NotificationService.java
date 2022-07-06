package com.dislinkt.notificationservice.service;

import com.dislinkt.grpc.*;
import com.dislinkt.notificationservice.model.Notification;
import com.dislinkt.notificationservice.repository.NotificationRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@EnableMongoRepositories("com.dislinkt.notificationservice.repository")
@GrpcService
public class NotificationService extends NotificationServiceGrpc.NotificationServiceImplBase {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void sendNotification(NotificationProto request, StreamObserver<NotificationResponse> responseObserver) {
        List<Long> seenList = new ArrayList<>();
        notificationRepository.save(new Notification(
                generateId(),
                request.getConnectionIdListList(),
                new ArrayList<Long>(),
                request.getText(),
                new Date()));
        NotificationResponse res = NotificationResponse.newBuilder().setMessage("success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserNotifications(UserIDNotification request, StreamObserver<NotificationsResponseList> responseObserver) {
        NotificationsResponseList.Builder res = NotificationsResponseList.newBuilder();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy. hh:mm:ss");
        boolean seen = false;
        for (Notification n : notificationRepository.findAll()) {
            seen = false;
            if (n.getSeenIdList().contains(request.getId()))
                seen = true;

            if (n.getConnectionIdList().contains(request.getId())) {
                res.addNotifications(NotificationsForUser.newBuilder()
                        .setNotificationsId(n.getId())
                        .setDateCreated(dateFormat.format(n.getDateAndTimeCreated()))
                        .setSeen(seen)
                        .setText(n.getText())
                        .build());
            }
        }
        responseObserver.onNext(res.build());
        responseObserver.onCompleted();
    }

    @Override
    public void markAsRead(UpdateNotification request, StreamObserver<NotificationResponse> responseObserver) {
        NotificationResponse.Builder res = NotificationResponse.newBuilder();
        try {
            Notification n = notificationRepository.findById(request.getNotificationId()).get();
            n.getSeenIdList().add(request.getUserId());
            notificationRepository.save(n);
            res.setMessage("success").setSuccess(true);
        } catch (Exception e){
            res.setMessage("failed").setSuccess(false);
            responseObserver.onNext(res.build());
            responseObserver.onCompleted();
        }
        responseObserver.onNext(res.build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeNotificationForUser(UpdateNotification request, StreamObserver<NotificationResponse> responseObserver) {
        NotificationResponse.Builder res = NotificationResponse.newBuilder();
        try {
            Notification n = notificationRepository.findById(request.getNotificationId()).get();
            n.getConnectionIdList().remove(request.getUserId());
            notificationRepository.save(n);
            res.setMessage("success").setSuccess(true);
        } catch (Exception e) {
            res.setMessage("failed").setSuccess(false);
            responseObserver.onNext(res.build());
            responseObserver.onCompleted();
        }
        responseObserver.onNext(res.build());
        responseObserver.onCompleted();
    }

    private long generateId() {
        for (long i = 1; i < 1000000000; i++) {
            try {
                notificationRepository.findById(i).get();
            } catch (NoSuchElementException e){
                return i;
            }
        }
        return 0;
    }
}
