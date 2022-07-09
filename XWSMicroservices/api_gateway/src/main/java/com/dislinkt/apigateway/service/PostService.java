package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.*;
import com.dislinkt.grpc.*;
import com.google.protobuf.Descriptors;
import com.sun.tools.jconsole.JConsoleContext;
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
    @Autowired
    ConnectionService connectionService;
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
        UserInfoChangeDTO userRes;
        PostCreateResponse res = postStub.createPost(req);
        String notificationText = "";
        if (res.getSuccess()) {
            long id = req.getUserId();
            userRes = authenticationService.getUser(post.userId);
            List<ConnectionDTO> connections = connectionService.getConnections(id);
            List<UserID> ids = new ArrayList<>();
            for (ConnectionDTO con : connections) {
                if (con.connectionState.equalsIgnoreCase("CONNECTED")) {
                    if (con.receiver != id)
                        ids.add(UserID.newBuilder().setId(con.receiver).build());
                    if (con.sender != id)
                        ids.add(UserID.newBuilder().setId(con.sender).build());
                }
                ConnectedUsers usersFinal = authenticationService.authStub.getUsersWithNotificationOn(ConnectedUsers.newBuilder().addAllUserIds(ids).build());
                notificationText = userRes.getUsername() + " - " + userRes.getName() + " " + userRes.getLastname() + " just added a new post !";
                notificationService.sendNotification(convertToLongId(usersFinal), notificationText);
            }
            return res.getAllFields();
        }
        return res.getAllFields();
    }

    private List<Long> convertToLongId(ConnectedUsers users){
       List <Long> ids = new ArrayList<>();
       for (UserID id : users.getUserIdsList()){
           ids.add(id.getId());
       }
       return ids;
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

    public List<PostUserDTO> getPosts(long id) {
        GetPosts req = GetPosts.newBuilder().setUserId(id).build();
        GetPostListResponse res = postStub.getPosts(req);
        return formatPosts(res);
    }

    private List<PostUserDTO> formatPosts(GetPostListResponse res) {
        List<PostProtoDTO> posts = new ArrayList<>();
        List<PostUserDTO> postsNew = new ArrayList<>();
        for (PostProto pp : res.getPostsList()){
            List<CommentDTO> comments = new ArrayList<>();
            List<CommentUserDTO> newComments = new ArrayList<>();
            List<Long> likes = new ArrayList<>();
            List<Long> dislikes = new ArrayList<>();
            for (CommentProto c : pp.getCommentsList()){
                CommentDTO comment = new CommentDTO(c.getPostId(), c.getUserId(), c.getContent(), c.getDateCreated());
                String username = authenticationService.getUser(c.getUserId()).getUsername();
                CommentUserDTO newComment = new CommentUserDTO(comment,username);
                newComments.add(newComment);
            }
            PostProtoDTO post = new PostProtoDTO(pp.getId(), pp.getUserId(), pp.getPostText(), pp.getImageString(),
                    newComments, pp.getLikedPostUsersList(), pp.getDislikedPostUsersList(), pp.getDateCreated());
            System.out.println(pp.getUserId());
            System.out.println(authenticationService.getUser(pp.getUserId()));
            String username = authenticationService.getUser(pp.getUserId()).getUsername();
            PostUserDTO newPost = new PostUserDTO(post,username);
            postsNew.add(newPost);
        }
        return postsNew;
    }

    public List<PostUserDTO> getFeed(long id) {
        List<ConnectionDTO> connections = connectionService.getConnections(id);
        List<Long> ids = new ArrayList<>();
        for (ConnectionDTO con : connections) {
            if (con.connectionState.equals("CONNECTED")){
                if (con.receiver != id)
                    ids.add(con.receiver);
            if (con.sender != id)
                ids.add(con.sender);
        }
    }
        GetFeed req = GetFeed.newBuilder().addAllUserId(ids).build();
        GetPostListResponse res = postStub.work(req);
        return formatPosts(res);
    }
}
