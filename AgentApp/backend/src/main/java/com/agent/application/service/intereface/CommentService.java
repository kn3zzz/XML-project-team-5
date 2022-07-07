package com.agent.application.service.intereface;

import com.agent.application.dto.AddCommentDTO;
import com.agent.application.dto.AddInterviewCommentDTO;
import com.agent.application.dto.CompanyRegistrationDTO;
import com.agent.application.model.Comment;
import com.agent.application.model.CommentInterview;
import com.agent.application.model.CommentSalary;
import com.agent.application.model.Company;

import java.util.List;

public interface CommentService {
    void addComment(AddCommentDTO comment) throws Exception;

    List<Comment> findAllByCompanyId(Long companyId);

    List<String> getPositionsByCompanyId(Long companyId);

    void addCommentSalary(CommentSalary comment, Long companyId) throws Exception;

    List<CommentSalary> getAllSalaryCommentsByCompanyId(Long companyId);

    void addCommentInterview(AddInterviewCommentDTO req) throws Exception;

    List<CommentInterview> getAllInterviewCommentsByCompanyId(Long companyId);
}
