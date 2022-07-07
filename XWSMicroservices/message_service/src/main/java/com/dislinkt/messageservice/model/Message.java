package com.dislinkt.messageservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private long senderId;
    private long receiverId;
    private String text;
    private Date dateCreated;

}
