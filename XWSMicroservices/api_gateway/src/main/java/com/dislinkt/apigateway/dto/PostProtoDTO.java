package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostProtoDTO {
        long id;
        long userId;
        String postText;
        String imageString;
        List<CommentUserDTO> comments;
        List<Long> likedPostUsers;
        List<Long> dislikedPostUsers;
        String dateCreated;
}
