package com.dislinkt.connectionservice.repository;

import com.dislinkt.connectionservice.model.Connection;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends MongoRepository <Connection, Long>{

    @Query("{id:'?0'}")
    public Connection getConnectionById(long id );
}
