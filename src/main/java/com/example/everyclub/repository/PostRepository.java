package com.example.everyclub.repository;

import com.example.everyclub.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p, u FROM Post p LEFT JOIN User u ON u.email = p.writer")
    public Page<Object[]> getPosts(Pageable pageable);

}
