package com.dislinkt.authenticationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.dislinkt.authenticationservice.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository <User, Long> {

    @Query("{email:'?0'}")
    public User getUserByEmail(String email);
    @Query("{username:'?0'}")
    public User getUserByUsername(String username);
}
