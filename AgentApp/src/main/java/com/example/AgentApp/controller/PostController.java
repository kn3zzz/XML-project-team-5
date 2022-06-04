package com.example.AgentApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AgentApp.model.Post;

@RestController
@RequestMapping("/post")
public class PostController {

	@GetMapping(path="/getPosts")
	public ResponseEntity<List<Post>> getUsers()
	{	
		List<Post> posts = new ArrayList<Post>();
		posts.add(new Post(1, "Hello"));
		posts.add(new Post(2, "Again"));
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
}
