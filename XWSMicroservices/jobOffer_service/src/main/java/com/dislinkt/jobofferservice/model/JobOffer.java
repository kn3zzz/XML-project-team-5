package com.dislinkt.jobofferservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("job_offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOffer {
    @Id
    private long id;
    private String companyName;
    private String position;
    private String jobDescription;
    private String dailyActivities;
    private String preconditions;
}
