package com.dislinkt.connectionservice.repository;

import com.dislinkt.connectionservice.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGraphRepository extends Neo4jRepository<User,Long> {
}
