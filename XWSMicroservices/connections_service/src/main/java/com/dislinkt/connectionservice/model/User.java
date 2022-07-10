package com.dislinkt.connectionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@Node("User")
public class User{
    @Id
    private long id;
    @Relationship(type = "CONNECTED", direction = Relationship.Direction.INCOMING)
    private Map<User, ConnectionGraph> connections;

    public User() {
        super();
        this.connections = new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<User, ConnectionGraph> getConnections() {
        return connections;
    }

    public void setConnections(Map<User, ConnectionGraph> connections) {
        this.connections = connections;
    }

}
