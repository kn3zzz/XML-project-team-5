package com.agent.application.mapper;

import com.agent.application.dto.CommentSalaryDTO;
import com.agent.application.model.CommentSalary;

public class CommentSalaryMapper {
    public CommentSalary mapAddCommentDtoToComment(CommentSalaryDTO dto) {
        CommentSalary c = new CommentSalary();
        c.setPosition(dto.getPosition());
        c.setPay(dto.getPay());
        c.setIsFormerEmployee(dto.isFormerEmployee());
        c.setFairPay(dto.isFairPay());
        return c;
    }

//    public CommentSalaryDTO mapCommentSalaryToCommentSalaryDto(CommentSalary comment) {
//        CommentSalaryDTO c = new CommentSalaryDTO();
//        c.setId(comment.getId());
//        c.setPosition(comment.getPosition());
//        c.setPay(comment.getPay());
//        c.setFormerEmployee(comment.getIsFormerEmployee());
//        c.setBonus(comment.getBonus());
//        c.setFairPay(comment.getFairPay());
//        c.setCompanyDTO(new CompanyMapper().mapCompanyToCompanyDto(comment.getCompany()));
//        return c;
//    }
}
