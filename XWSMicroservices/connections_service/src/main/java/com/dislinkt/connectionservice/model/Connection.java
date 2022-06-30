package com.dislinkt.connectionservice.model;

import com.dislinkt.connectionservice.enums.ConnectionState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("connections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
    @Id
    private long id;
    private long sender;
    private long receiver;
    private ConnectionState connectionState;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public String getConnectionStateString(){return stateToString(this.connectionState); }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }

    public void setConnectionStateString(String connectionState) {this.connectionState = this.stringToState(connectionState); }

    public Connection(long id, long sender, long receiver, String connectionState){
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
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
