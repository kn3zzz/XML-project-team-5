package com.dislinkt.postservice.service;

import com.dislinkt.grpc.*;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.repository.PostRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories("com.dislinkt.postservice.repository")
@GrpcService
public class PostService  extends PostServiceGrpc.PostServiceImplBase {
    @Autowired
    private PostRepository postRepository;

    @Override
    public void createPost(PostCreate request, StreamObserver<PostCreateResponse> responseObserver){
        postRepository.save( new Post(
              2,
              request.getUserId(),
              request.getPostText(),
              request.getImageString()
        ));
        PostCreateResponse res = PostCreateResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
    @Override
    public void likePost(LikePost request, StreamObserver<LikePostResponse> responseObserver){
        Post post = postRepository.getPostById(request.getPostId());
        post.addLikeId(request.getUserId());
        postRepository.save(post);
    }
    @Override
    public void dislikePost(DisLikePost request, StreamObserver<DisLikePostResponse> responseObserver){
        Post post = postRepository.getPostById(request.getPostId());
        post.addDislikeId(request.getUserId());
        postRepository.save(post);
    }

    @Override
    public void commentPost(PostComment request, StreamObserver<PostCommentResponse> responseObserver) {
        Post post = postRepository.getPostById(request.getPostId());
        post.addComment(request.getPostId(),request.getUserId(),request.getContent());
        postRepository.save(post);
    }
}
