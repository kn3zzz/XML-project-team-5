package com.dislinkt.connectionservice.model;

import com.dislinkt.connectionservice.enums.ConnectionState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;
import org.springframework.data.annotation.Id;



@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class ConnectionGraph {
    @Id
    @GeneratedValue
    private long id;
    @Property("connectionStatus")
    private ConnectionState connectionState;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public String getConnectionStateString(){return stateToString(this.connectionState); }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }

    public void setConnectionStateString(String connectionState) {this.connectionState = this.stringToState(connectionState); }

    public ConnectionGraph(long id, long sender, long receiver, String connectionState){
        this.id = id;
        this.connectionState = this.stringToState(connectionState);
    }
    private ConnectionState stringToState(String state){
        switch (state) {
            case "PENDING":
                return ConnectionState.PENDING;
            case "BLOCKED":
                return ConnectionState.BLOCKED;
            case "CONNECTED":
                return ConnectionState.CONNECTED;
            default:
                return ConnectionState.IDLE;
        }
    }

    private String stateToString(ConnectionState state){
        switch (state) {
            case PENDING:
                return "PENDING";
            case BLOCKED:
                return "BLOCKED";
            case CONNECTED:
                return "CONNECTED";
            default:
                return "IDLE";
        }
    }

}
