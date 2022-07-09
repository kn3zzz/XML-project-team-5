package com.agent.application.dto;

import com.agent.application.model.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferDislinktDTO {
    public String  company;
    public String position;
    public String jobDescription;
    public String dailyActivities;
    public String preconditions;
}
