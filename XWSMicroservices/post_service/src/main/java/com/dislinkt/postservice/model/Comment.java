package com.dislinkt.postservice.model;

public class Comment {
    private long postId;
    private long userId;
    private String content;

    public Comment(long postId, long userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

    public long getPostId() {
        return postId;
    }

    public long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
