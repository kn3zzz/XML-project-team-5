package com.agent.application.repository;

import com.agent.application.model.Comment;
import com.agent.application.model.CommentInterview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentInterviewRepository extends JpaRepository<CommentInterview, Long> {

}
