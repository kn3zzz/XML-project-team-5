package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class NewUserDTO {
    public String name;
    public String lastname;
    public String username;
    public String password;
    public String email;
    public String  phoneNumber;
    public String gender;
    public String birthDate;
    public boolean privateProfile;


}
