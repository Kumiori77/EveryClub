package com.example.everyclub.repository;

import com.example.everyclub.entity.Post;
import com.example.everyclub.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p, w, count(r) " +
            "FROM Post p LEFT JOIN p.writer w LEFT JOIN Reply r ON r.post = p " +
            "WHERE p.team.tno = :tno GROUP BY p",
            countQuery = "SELECT COUNT(p) FROM Post p WHERE p.team.tno = :tno")
    Page<Object[]> getPosts(Pageable pageable, @Param("tno") Long tno);

    @Query(value = "SELECT p, w " +
    "FROM Post p LEFT JOIN p.writer w " +
    "WHERE p.pno = :pno GROUP BY p")
    Object getPostByPno(@Param("pno")Long pno);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.team.tno=:tno")
    void removeByTno(@Param("tno") Long tno);

}
//WHERE p.team.tno = :tno