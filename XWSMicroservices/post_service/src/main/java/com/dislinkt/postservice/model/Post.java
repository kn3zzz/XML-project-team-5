package com.dislinkt.postservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    private long id;
    private long userId;
    private String postText;
    private String imageString;
    private ArrayList<Comment> comments;
    private ArrayList<Long> likedPostUsers;
    private ArrayList<Long> dislikedPostUsers;
    private Date date;


    public Post(long id, long userId, String postText, String imageString,Date date) {
        this.id = id;
        this.userId = userId;
        this.postText = postText;
        this.imageString = imageString;
        this.comments = new ArrayList<Comment>();
        this.dislikedPostUsers = new ArrayList<Long>();
        this.likedPostUsers = new ArrayList<Long>();
        this.date =  date;
    }


    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getPostText() {
        return postText;
    }

    public String getImageString() {
        return imageString;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Long> getLikedPostUsers() {
        return likedPostUsers;
    }

    public ArrayList<Long> getDislikedPostUsers() {
        return dislikedPostUsers;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setLikedPostUsers(ArrayList<Long> likedPostUsers) {
        this.likedPostUsers = likedPostUsers;
    }

    public void setDislikedPostUsers(ArrayList<Long> dislikedPostUsers) {
        this.dislikedPostUsers = dislikedPostUsers;
    }

    public void addLikeId(long userId){
        this.likedPostUsers.add(userId);
    }
    public void removeLikeId(int index){
        this.likedPostUsers.remove(index);
    }
    public void removeDislikeId(int index){
        this.dislikedPostUsers.remove(index);
    }
    public void addDislikeId(long userId){
        this.dislikedPostUsers.add(userId);
    }
    public void addComment(long postId, long userId, String content,Date commentDate){
        Comment comment = new Comment(postId,userId,content,commentDate);
        this.comments.add(comment);
    }
}
