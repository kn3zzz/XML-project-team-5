package com.dislinkt.connectionservice.repository;

import com.dislinkt.connectionservice.model.User;
import org.neo4j.springframework.data.repository.Neo4jRepository;

public interface UserGraphRepository extends Neo4jRepository<User,Long> {
}
