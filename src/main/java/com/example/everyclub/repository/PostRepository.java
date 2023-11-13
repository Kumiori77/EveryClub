package com.example.everyclub.repository;

import com.example.everyclub.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p")
    public Page<Post> getPosts(Pageable pageable);

}
