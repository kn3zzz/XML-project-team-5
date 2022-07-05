package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.*;
import com.dislinkt.grpc.*;
import com.google.protobuf.Descriptors;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    NotificationService notificationService;
    @GrpcClient("post-grpc-service")
    PostServiceGrpc.PostServiceBlockingStub postStub;

    public Map<Descriptors.FieldDescriptor, Object> createPost(@RequestBody CreatePostDTO post) {
        PostCreate req = PostCreate.newBuilder()
                .setPostId(post.postId)
                .setUserId(post.userId)
                .setPostText(post.postText)
                .setImageString(post.imageString)
                .build();
        UserInfoChangeDTO userRes;
        List<Long> users = new ArrayList<>();
        PostCreateResponse res = postStub.createPost(req);
        String notificationText = "";
        if (res.getSuccess()){
            userRes = authenticationService.getUser(post.userId);
            notificationText = userRes.getUsername() + " - " + userRes.getName() + " " + userRes.getLastname() + " just added a new post !";
            users = new ArrayList<>();
            users.add(2L);
            users.add(3L);
            notificationService.sendNotification(users, notificationText);
        }
        return  res.getAllFields();

    }
    public Map<Descriptors.FieldDescriptor, Object> likePost(@RequestBody ReactionPostDTO post) {
        LikePost req = LikePost.newBuilder()
                .setPostId(post.postId)
                .setUserId(post.userId)
                .build();
        LikePostResponse res = postStub.likePost(req);
        return  res.getAllFields();
    }
    public Map<Descriptors.FieldDescriptor, Object> dislikePost(@RequestBody ReactionPostDTO post) {
        DisLikePost req = DisLikePost.newBuilder()
                .setPostId(post.postId)
                .setUserId(post.userId)
                .build();
        DisLikePostResponse res = postStub.dislikePost(req);
        return  res.getAllFields();
    }
    public Map<Descriptors.FieldDescriptor, Object> commentPost(@RequestBody CommentDTO post) {
        PostComment req = PostComment.newBuilder()
                .setPostId(post.postId)
                .setUserId(post.userId)
                .setContent(post.content)
                .build();
        PostCommentResponse res = postStub.commentPost(req);
        return res.getAllFields();
    }
}
