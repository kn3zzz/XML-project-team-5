package com.dislinkt.notificationservice.repository;

import com.dislinkt.notificationservice.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository <Notification, Long> {

}
