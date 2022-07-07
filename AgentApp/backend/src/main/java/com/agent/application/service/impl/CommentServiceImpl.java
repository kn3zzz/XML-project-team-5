package com.agent.application.service.impl;

import com.agent.application.dto.AddCommentDTO;
import com.agent.application.dto.AddInterviewCommentDTO;
import com.agent.application.dto.CommentSalaryDTO;
import com.agent.application.mapper.CommentInterviewMapper;
import com.agent.application.mapper.CommentMapper;
import com.agent.application.model.*;
import com.agent.application.repository.CommentInterviewRepository;
import com.agent.application.repository.CommentRepository;
import com.agent.application.repository.CommentSalaryRepository;
import com.agent.application.repository.CompanyRepository;
import com.agent.application.service.intereface.CommentService;
import com.agent.application.service.intereface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentSalaryRepository commentSalaryRepository;
    @Autowired
    private CommentInterviewRepository commentInterviewRepository;

    @Override
    public void addComment(AddCommentDTO dto) throws Exception {
        Comment comment = new CommentMapper().mapAddCommentDtoToComment(dto);
        Company company = companyRepository.findCompanyById(dto.getCompanyId());
        if(!company.isActive())
            throw new Exception("Company is not active");
        comment.setCompany(company);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByCompanyId(Long companyId) {
        List<Comment> comments = new ArrayList<>();
        for (Comment comment: commentRepository.findAll()) {
            if(comment.getCompany().getId() == companyId) {
                comments.add(comment);
            }
        }
        return comments;
    }

    @Override
    public List<String> getPositionsByCompanyId(Long companyId) {
        List<String> positionsName = new ArrayList<>();
        Company company = companyRepository.findCompanyById(companyId);
        if(company == null || !company.isActive()) {
            return null;
        }
        for(JobOffer offer: company.getJobOffers()){
            positionsName.add(offer.getPosition().getName());
        }
        return positionsName;
    }

    @Override
    public void addCommentSalary(CommentSalary comment, Long companyId) throws Exception {
        Company company = companyRepository.findCompanyById(companyId);
        if (!company.isActive())
            throw new Exception("Company is not active");
        comment.setCompany(company);
        commentSalaryRepository.save(comment);
    }

    @Override
    public List<CommentSalary> getAllSalaryCommentsByCompanyId(Long companyId) {
        List<CommentSalary> salaries = new ArrayList<>();
        for (CommentSalary comment: commentSalaryRepository.findAll()) {
            if(comment.getCompany().getId() == companyId) {
                salaries.add(comment);
            }
        }
        return salaries;
    }

    @Override
    public void addCommentInterview(AddInterviewCommentDTO reqDto) throws Exception {
        CommentInterview comment = new CommentInterviewMapper().mapAddCommentInterviewToCommentInterview(reqDto);
        Company company = companyRepository.findCompanyById(reqDto.getCompanyId());
        if(!company.isActive())
            throw new Exception("Company is not active");
        comment.setCompany(company);
        commentInterviewRepository.save(comment);
    }

    @Override
    public List<CommentInterview> getAllInterviewCommentsByCompanyId(Long companyId) {
        List<CommentInterview> interviews = new ArrayList<>();
        for (CommentInterview comment: commentInterviewRepository.findAll()) {
            if(comment.getCompany().getId() == companyId) {
                interviews.add(comment);
            }
        }
        return interviews;
    }
}
