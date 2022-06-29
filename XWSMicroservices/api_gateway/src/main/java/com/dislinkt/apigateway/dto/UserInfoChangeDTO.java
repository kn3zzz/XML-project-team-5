package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoChangeDTO {
    public String name;
    public String lastname;
    public String username;
    public String email;
    public String phoneNumber;
    public String gender;
    public String birthDate;
    public String biography;
    public String workingExperience;
    public String education;
    public String skills;
    public String interests;
    public boolean privateProfile;
    public boolean notificationsOn;
    public long id;
}
