package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentUserDTO {
    public long postId;
    public String username;
    public long userId;
    public String content;
    public String dateCreated;

    public CommentUserDTO(CommentDTO comment,String username){
        this.postId = comment.postId;
        this.username = username;
        this.userId = comment.userId;
        this.content = comment.content;
        this.dateCreated = comment.dateCreated;
    }
}
