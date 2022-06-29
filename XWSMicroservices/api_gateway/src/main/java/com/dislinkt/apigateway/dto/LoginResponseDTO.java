package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDTO {

    long id;
    String username;
    boolean notificationsOn;
    String role;

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", notificationsOn=" + notificationsOn +
                ", role='" + role + '\'' +
                '}';
    }
}
