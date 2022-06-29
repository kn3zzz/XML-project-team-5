package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    public long postId;
    public long userId;
    public String content;
}
