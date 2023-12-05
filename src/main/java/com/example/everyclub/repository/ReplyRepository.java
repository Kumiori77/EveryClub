package com.example.everyclub.repository;

import com.example.everyclub.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r WHERE r.post.pno = :pno")
    List<Object> getRepliesByPno(@Param("pno") Long pno);

    @Modifying
    @Query("DELETE FROM Reply r WHERE r.post.pno = :pno ")
    void deleteByPno(@Param("pno")Long pno);

    @Modifying
    @Query(value = "DELETE FROM reply r WHERE r.post_pno IN " +
    "(SELECT p.pno FROM post p WHERE p.team_tno=:tno)", nativeQuery = true)
    void deleteByTno(@Param("tno")Long tno);


}
