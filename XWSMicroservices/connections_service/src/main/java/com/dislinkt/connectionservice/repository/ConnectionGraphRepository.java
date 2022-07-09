package com.dislinkt.connectionservice.repository;


import com.dislinkt.connectionservice.model.ConnectionGraph;
import com.dislinkt.connectionservice.model.User;
import org.neo4j.springframework.data.repository.Neo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;


import java.util.List;

public interface ConnectionGraphRepository extends Neo4jRepository<ConnectionGraph,Long> {
    @Query("MATCH (u1:User {id: $0}), (u2:User {id: $1}) CREATE (u1)-[r:CONNECTION {connectionStatus: $2}]->(u2) RETURN r")
    ConnectionGraph saveConnection(Long initiatorId, Long receiverId, String connectionStatus);
    @Query("MATCH (:User {id: $0})-[:CONNECTION*2 {connectionStatus: 'CONNECTED'}]->(u:User) RETURN DISTINCT u")
    List<User> findSecondLevelConnections(Long userId);
    @Query("MATCH (u1:User {id: $0})-[r:CONNECTION]->(u2:User {id: $1}) RETURN r")
    ConnectionGraph getConnection(Long initiatorId, Long receiverId);
}
