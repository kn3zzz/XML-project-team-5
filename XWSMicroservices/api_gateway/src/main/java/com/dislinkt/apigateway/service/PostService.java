package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.*;
import com.dislinkt.grpc.*;
import com.google.protobuf.Descriptors;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    @GrpcClient("post-grpc-service")
    PostServiceGrpc.PostServiceBlockingStub postStub;

    public Map<Descriptors.FieldDescriptor, Object> createPost(@RequestBody CreatePostDTO post) {
        PostCreate req = PostCreate.newBuilder()
                .setPostId(post.postId)
                .setUserId(post.userId)
                .setPostText(post.postText)
                .setImageString(post.imageString)
                .setDateCreated(post.dateCreated)
                .build();
        PostCreateResponse res = postStub.createPost(req);
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
                .setDateCreated(post.dateCreated)
                .build();
        PostCommentResponse res = postStub.commentPost(req);
        return res.getAllFields();
    }

    public List<PostProtoDTO> getPosts(long id) {
        GetPosts req = GetPosts.newBuilder().setUserId(id).build();
        GetPostListResponse res = postStub.getPosts(req);
        return formatPosts(res);
    }

    private List<PostProtoDTO> formatPosts(GetPostListResponse res) {
        List<PostProtoDTO> posts = new ArrayList<>();
        for (PostProto pp : res.getPostsList()){
            List<CommentDTO> comments = new ArrayList<>();
            List<Long> likes = new ArrayList<>();
            List<Long> dislikes = new ArrayList<>();
            for (CommentProto c : pp.getCommentsList()){
                comments.add(new CommentDTO(c.getPostId(), c.getUserId(), c.getContent(), c.getDateCreated()));
            }
            posts.add(new PostProtoDTO(pp.getId(), pp.getUserId(), pp.getPostText(), pp.getImageString(),
                    comments, pp.getLikedPostUsersList(), pp.getDislikedPostUsersList(), pp.getDateCreated()));
        }
        return posts;
    }
}
