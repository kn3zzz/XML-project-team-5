package com.dislinkt.connectionservice.model;

import com.dislinkt.connectionservice.enums.Gender;
import com.dislinkt.connectionservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.Id;


import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Node
public class User{
    @Id
    private long id;
    @Relationship(type = "CONNECTED", direction = Relationship.Direction.INCOMING)
    private Map<User,Connection> connections;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<User, Connection> getConnections() {
        return connections;
    }

    public void setConnections(Map<User, Connection> connections) {
        this.connections = connections;
    }
}
