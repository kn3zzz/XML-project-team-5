package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.NotificationDTO;
import com.dislinkt.apigateway.dto.UpdateNotificationDTO;
import com.dislinkt.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @GrpcClient("notification-grpc-service")
    NotificationServiceGrpc.NotificationServiceBlockingStub notificationStub;

    public List<NotificationDTO> getUserNotifications(long id) {
        UserIDNotification req = UserIDNotification.newBuilder().setId(id).build();
        NotificationsResponseList res = notificationStub.getUserNotifications(req);
        return convertToNotificationDTOList(res);
    }

    public Boolean deleteNotificationForUser(UpdateNotificationDTO updateDTO) {
        UpdateNotification req = UpdateNotification.newBuilder()
                .setNotificationId(updateDTO.notificationId)
                .setUserId(updateDTO.userId)
                .build();
        NotificationResponse res = notificationStub.removeNotificationForUser(req);
        if (res.getSuccess())
            return true;
        return false;
    }

    public Boolean sendNotification(List<Long> list, String text){
        NotificationProto req = NotificationProto.newBuilder().addAllConnectionIdList(list).setText(text).build();
        NotificationResponse res = notificationStub.sendNotification(req);
        if (res.getSuccess())
            return true;
        return false;
    }

    public Boolean markAsRead(UpdateNotificationDTO updateDTO) {
        UpdateNotification req = UpdateNotification.newBuilder()
                .setNotificationId(updateDTO.notificationId)
                .setUserId(updateDTO.userId)
                .build();
        NotificationResponse res = notificationStub.markAsRead(req);
        if (res.getSuccess())
            return true;
        return false;
    }

    public List<NotificationDTO> convertToNotificationDTOList(NotificationsResponseList responses) {
        List<NotificationDTO> retList = new ArrayList<NotificationDTO>();
        for (int i = 0; i < responses.getNotificationsCount(); i++) {
            NotificationsForUser res = responses.getNotifications(i);
            retList.add(new NotificationDTO(res.getNotificationsId(), res.getText(), res.getDateCreated(), res.getSeen()));
        }
        return retList;
    }
}
