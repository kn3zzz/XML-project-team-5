package com.dislinkt.postservice.service;

import com.dislinkt.grpc.*;
import com.dislinkt.postservice.model.Comment;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.repository.PostRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
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
              this.getMaxId(),
              request.getUserId(),
              request.getPostText(),
              request.getImageString(),
              dateCreated
        ));
        PostCreateResponse res = PostCreateResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    public long getMaxId(){
        long max=0;
       List<Post> posts = postRepository.findAll();
       if(posts == null){
           return 0;
       }
       for(Post p:posts){
           if(p.getId() > max)
               max = p.getId();
       }
       return ++max;
    }
    @Override
    public void likePost(LikePost request, StreamObserver<LikePostResponse> responseObserver){
            Post post = postRepository.findById(request.getPostId()).get();
            List<Long> likedUsersIds = post.getLikedPostUsers();
            List<Long> dislikedUsersIds = post.getDislikedPostUsers();
        int flag = 0;
        int flag2 = 0;
        for (int j = 0; j < likedUsersIds.size(); j++) {
            if (request.getUserId() == likedUsersIds.get(j)) {
                post.removeLikeId(j);
                flag = 1;
                flag2 = 1;
            }
        }
        if(flag2 == 0) {
            for (int j = 0; j < dislikedUsersIds.size(); j++) {
                if (request.getUserId() == dislikedUsersIds.get(j)) {
                    post.removeDislikeId(j);
                    post.addLikeId(request.getUserId());
                    flag = 1;
                }
            }
        }
            if(flag == 0)
                post.addLikeId(request.getUserId());

            postRepository.save(post);
            LikePostResponse res = LikePostResponse.newBuilder().setMessage("Success").setSuccess(true).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();

    }
    @Override
    public void dislikePost(DisLikePost request, StreamObserver<DisLikePostResponse> responseObserver) {
        Post post = postRepository.findById(request.getPostId()).get();
        List<Long> likedUsersIds = post.getLikedPostUsers();
        List<Long> dislikedUsersIds = post.getDislikedPostUsers();
        System.out.println(request.getUserId());
        int flag = 0;
        int flag2 = 0;
        for (int j = 0; j < dislikedUsersIds.size(); j++) {
            if (request.getUserId() == dislikedUsersIds.get(j)) {
                post.removeDislikeId(j);
                flag = 1;
                flag2 = 1;
            }
        }
        if(flag2 == 0) {
            for (int j = 0; j < likedUsersIds.size(); j++) {
                if (request.getUserId() == likedUsersIds.get(j)) {
                    post.removeLikeId(j);
                    post.addDislikeId(request.getUserId());
                    flag = 1;
                }
            }
        }
            if (flag == 0)
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

    @Override
    public void getPosts( GetPosts request, StreamObserver<GetPostListResponse> responseObserver){
        List<PostProto> postsProto = new ArrayList<>();
        for (Post p : postRepository.findAll()) {
            if(p.getUserId()==request.getUserId()) {
                List<CommentProto> commentsProto = new ArrayList<>();
                for (Comment c : p.getComments()) {
                    commentsProto.add(CommentProto.newBuilder().setUserId(c.getUserId()).setDateCreated(c.getDate().toString()).setPostId(p.getId()).setContent(c.getContent()).build());
                }
                PostProto postProto = PostProto.newBuilder()
                        .setId(p.getId())
                        .setUserId(p.getUserId())
                        .setPostText(p.getPostText())
                        .setImageString(p.getImageString())
                        .addAllComments(commentsProto)
                        .addAllLikedPostUsers(p.getLikedPostUsers())
                        .addAllDislikedPostUsers(p.getDislikedPostUsers())
                        .setDateCreated(p.getDate().toString())
                        .build();

                postsProto.add(postProto);
            }
        }
        GetPostListResponse res = GetPostListResponse.newBuilder()
                .addAllPosts(postsProto)
                .build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
    @Override
    public void work( GetFeed request, StreamObserver<GetPostListResponse> responseObserver){
        List<PostProto> postsProto = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();
        userIds = request.getUserIdList();
        for (Post p : postRepository.findAll()) {
            for (long id : userIds) {
                if (p.getUserId() == id) {
                    List<CommentProto> commentsProto = new ArrayList<>();
                    for (Comment c : p.getComments()) {
                        commentsProto.add(CommentProto.newBuilder().setUserId(c.getUserId()).setDateCreated(c.getDate().toString()).setPostId(p.getId()).setContent(c.getContent()).build());
                    }
                    PostProto postProto = PostProto.newBuilder()
                            .setId(p.getId())
                            .setUserId(p.getUserId())
                            .setPostText(p.getPostText())
                            .setImageString(p.getImageString())
                            .addAllComments(commentsProto)
                            .addAllLikedPostUsers(p.getLikedPostUsers())
                            .addAllDislikedPostUsers(p.getDislikedPostUsers())
                            .setDateCreated(p.getDate().toString())
                            .build();

                    postsProto.add(postProto);
                }
            }
        }
        GetPostListResponse res = GetPostListResponse.newBuilder()
                .addAllPosts(postsProto)
                .build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }


}
