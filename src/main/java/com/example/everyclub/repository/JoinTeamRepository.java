package com.example.everyclub.repository;

import com.example.everyclub.entity.JoinTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JoinTeamRepository extends JpaRepository<JoinTeam, Long> {

    @Query("SELECT t FROM JoinTeam j LEFT JOIN Team t ON j.team = t " +
            "WHERE j.user.email = :email")
    Object[] getTeamByEmail(@Param("email")String email);

    @Query(value = "SELECT t.team_name, MAX(like_team) as max_like_team FROM join_team j LEFT JOIN team t ON j.team_tno = t.tno " +
            "WHERE t.tno NOT IN " +
            "(SELECT t.tno FROM join_team j LEFT JOIN team t ON j.team_tno = t.tno " +
            "WHERE j.user_email = :email)" +
            "GROUP BY t.team_name ORDER BY max_like_team DESC", nativeQuery = true)
    List<Object[]> getTeamWithoutEmail(@Param("email")String email);

}
