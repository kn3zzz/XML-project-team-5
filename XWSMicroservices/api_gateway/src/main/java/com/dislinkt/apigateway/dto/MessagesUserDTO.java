package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagesUserDTO {
    public long id;
    public String name;
    public String lastname;
    public String username;
}
