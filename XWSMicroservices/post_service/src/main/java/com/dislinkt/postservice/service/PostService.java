package com.dislinkt.postservice.service;

import com.dislinkt.grpc.*;
import com.dislinkt.postservice.model.Comment;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.repository.PostRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EnableMongoRepositories("com.dislinkt.postservice.repository")
@GrpcService
public class PostService  extends PostServiceGrpc.PostServiceImplBase {
    @Autowired
    private PostRepository postRepository;

    @Override
    public void createPost(PostCreate request, StreamObserver<PostCreateResponse> responseObserver){
        Date dateCreated;
        try {
            dateCreated = new SimpleDateFormat("yyyy-MM-dd HH-mm").parse(request.getDateCreated());
        } catch (Exception e){
            dateCreated = new Date();
        }
        postRepository.save( new Post(
              3,
              request.getUserId(),
              request.getPostText(),
              request.getImageString(),
              dateCreated
        ));
        PostCreateResponse res = PostCreateResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
    @Override
    public void likePost(LikePost request, StreamObserver<LikePostResponse> responseObserver){
        Post post = postRepository.findById(request.getPostId()).get();
        post.addLikeId(request.getUserId());
        postRepository.save(post);
        LikePostResponse res = LikePostResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
    @Override
    public void dislikePost(DisLikePost request, StreamObserver<DisLikePostResponse> responseObserver){
        Post post = postRepository.findById(request.getPostId()).get();
        post.addDislikeId(request.getUserId());
        postRepository.save(post);
        DisLikePostResponse res = DisLikePostResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void commentPost(PostComment request, StreamObserver<PostCommentResponse> responseObserver) {
        Date dateCreated;
        try {
            dateCreated = new SimpleDateFormat("yyyy-MM-dd HH-mm").parse(request.getDateCreated());
        } catch (Exception e){
            dateCreated = new Date();
        }
        Post post = postRepository.findById(request.getPostId()).get();
        post.addComment(request.getPostId(),request.getUserId(),request.getContent(),dateCreated);
        postRepository.save(post);
        PostCommentResponse res = PostCommentResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
    /*
    public void getPosts( GetPosts request, StreamObserver<GetPostListResponse> responseObserver){
        GetPostListResponse.Builder res = GetPostListResponse.newBuilder();
        ArrayList<Comment> comments = new ArrayList<>();
        for (Post p : postRepository.findAll()) {
            int i = 0;

                res.addPosts(GetPostsResponse.newBuilder()
                        .setId(p.getId())
                        .setUserId(p.getUserId())
                        .setPostText(p.getPostText())
                        .setImageString(p.getImageString())
                        .setComments(p.getComments())
                        .setLikedPostUsers(p.getLikedPostUsers())
                        .setDislikedPostUsers(p.getDislikedPostUsers())
                        .build());

        }

        responseObserver.onNext(res.build());
        responseObserver.onCompleted();

    }

     */
}
