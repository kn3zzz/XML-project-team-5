package com.dislinkt.apigateway.controller;

import com.dislinkt.apigateway.dto.*;
import com.dislinkt.apigateway.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody CreatePostDTO post){
        System.out.println(post.postText);
        System.out.println(post.dateCreated);
        return new ResponseEntity<>(postService.createPost(post), HttpStatus.OK);
    }
    @PostMapping("/likePost")
    public ResponseEntity<?> likePost(@RequestBody ReactionPostDTO post){
        return new ResponseEntity<>(postService.likePost(post), HttpStatus.OK);
    }
    @PostMapping("/dislikePost")
    public ResponseEntity<?> dislikePost(@RequestBody ReactionPostDTO post){
        return new ResponseEntity<>(postService.dislikePost(post), HttpStatus.OK);
    }
    @PostMapping("/commentPost")
    public ResponseEntity<?> commentPost(@RequestBody CommentDTO post){
        return new ResponseEntity<>(postService.commentPost(post), HttpStatus.OK);
    }
    @GetMapping("/getPosts/{id}")
    public ResponseEntity<?> getPosts(@PathVariable("id") long id){
        return new ResponseEntity<>(postService.getPosts(id),HttpStatus.OK);
    }
    @GetMapping("/getFeed/{id}")
    public ResponseEntity<?> getFeed(@PathVariable("id") long id){
        return new ResponseEntity<>(postService.getFeed(id),HttpStatus.OK);
    }
}
