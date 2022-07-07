package com.agent.application.mapper;


import com.agent.application.dto.AddInterviewCommentDTO;
import com.agent.application.model.CommentInterview;

public class CommentInterviewMapper {
    public CommentInterview mapAddCommentInterviewToCommentInterview(AddInterviewCommentDTO dto) {
        CommentInterview c = new CommentInterview();
        c.setPosition(dto.getPosition());
        c.setTitle(dto.getTitle());
        c.setTechnicalInterview(dto.getTechnicalInterview());
        c.setRating(dto.getRating());
        return c;
    }

//    public CommentInterviewDTO mapCommentInterviewToCommentInterviewDto(CommentInterview c) {
//        CommentInterviewDTO dto = new CommentInterviewDTO();
//        dto.setId(c.getId());
//        dto.setPosition(c.getPosition());
//        dto.setTitle(c.getTitle());
//        dto.setHrInterview(c.getHrInterview());
//        dto.setTechnicalInterview(c.getTechnicalInterview());
//        dto.setRating(c.getRating());
//        dto.setCompany(new CompanyMapper().mapCompanyToCompanyDto(c.getCompany()));
//        return dto;
//    }
}
