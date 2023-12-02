package com.example.everyclub.repository;

import com.example.everyclub.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r WHERE r.post.pno = :pno")
    List<Object> getRepliesByPno(@Param("pno") Long pno);
}
