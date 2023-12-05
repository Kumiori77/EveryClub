package com.example.everyclub.repository;

import com.example.everyclub.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    // 가입하지 않은 모든 모임 리스트 별점순 정렬
    @Query(value = "SELECT t.tno, t.team_name, t.description, t.like_team FROM team t WHERE t.tno NOT IN " +
            "(SELECT t.tno FROM join_team j LEFT JOIN team t ON j.team_tno = t.tno " +
            "WHERE j.user_email = :email) " +
            "ORDER BY t.like_team DESC", nativeQuery = true)
    List<Object[]> getTeamNotJoined(@Param("email")String email);


    // 모든 모임 리스트 별점 순 정렬
    @Query("SELECT t FROM Team t ORDER BY t.likeTeam DESC")
    List<Object> getAllTeams();

}
