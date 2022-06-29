package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDTO {
    public long id;
    public long sender;
    public long receiver;
    public String connectionState;

    @Override
    public String toString() {
        return "ConnectionDTO{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", connectionState='" + connectionState + '\'' +
                '}';
    }
}
