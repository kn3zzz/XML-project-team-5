package com.agent.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRegistrationDTO {
    private Long id;
    private String name;
    private String description;
    private String phoneNumber;
    private String userEmail;
}
