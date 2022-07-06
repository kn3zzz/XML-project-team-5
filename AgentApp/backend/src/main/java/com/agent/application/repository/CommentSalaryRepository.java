package com.agent.application.repository;

import com.agent.application.model.Comment;
import com.agent.application.model.CommentSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentSalaryRepository extends JpaRepository<CommentSalary, Long> {

}
