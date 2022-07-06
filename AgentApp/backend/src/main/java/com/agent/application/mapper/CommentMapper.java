package com.agent.application.mapper;

import com.agent.application.dto.AddCommentDTO;
import com.agent.application.model.Comment;

public class CommentMapper {
    public Comment mapAddCommentDtoToComment(AddCommentDTO dto) {
        Comment c = new Comment();
        c.setTitle(dto.getTitle());
        c.setContent(dto.getContent());
        c.setPosition(dto.getPosition());
        c.setRating(dto.getRating());
        return c;
    }
}
