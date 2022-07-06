package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    public long notificationsId;
    public String text;
    public String dateCreated;
    public boolean seen;
}
