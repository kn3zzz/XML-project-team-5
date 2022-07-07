package com.agent.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddInterviewCommentDTO {
    private String position;
    private String title;
    private String technicalInterview;
    private Integer rating;
    private Long companyId;
}

