package com.dislinkt.messageservice.repository;

import com.dislinkt.messageservice.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository <Chat, String> {
}
