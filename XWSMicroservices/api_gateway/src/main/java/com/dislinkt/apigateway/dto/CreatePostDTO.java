package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDTO {
    public long postId;
    public long userId;
    public String postText;
    public String imageString;
    public String dateCreated;

}
