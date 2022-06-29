package com.dislinkt.postservice.repository;

import com.dislinkt.postservice.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post,Long> {
    @Query("{id:'?0'}")
    public Post getPostById(Long id);
}
