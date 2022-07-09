package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferDTO {
    public String company;
    public String position;
    public String jobDescription;
    public String dailyActivities;
    public String preconditions;
}