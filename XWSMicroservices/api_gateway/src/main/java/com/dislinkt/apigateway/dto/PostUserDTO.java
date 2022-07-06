package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostUserDTO {
    long id;
    String username;
    long userId;
    String postText;
    String imageString;
    List<CommentUserDTO> comments;
    List<Long> likedPostUsers;
    List<Long> dislikedPostUsers;
    String dateCreated;

    public PostUserDTO(PostProtoDTO post,String username){
        this.id = post.id;
        this.username = username;
        this.postText = post.postText;
        this.imageString = post.imageString;
        this.comments = post.comments;
        this.likedPostUsers = post.likedPostUsers;
        this.dislikedPostUsers = post.dislikedPostUsers;
        this.dateCreated = post.dateCreated;
    }
}
