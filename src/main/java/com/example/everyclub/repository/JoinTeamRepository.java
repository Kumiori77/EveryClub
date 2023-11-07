package com.example.everyclub.repository;

import com.example.everyclub.entity.JoinTeam;
import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JoinTeamRepository extends JpaRepository<JoinTeam, Long> {

    // 내가 가입한 모임 리스트
    @Query("SELECT t FROM JoinTeam j LEFT JOIN Team t ON j.team = t " +
            "WHERE j.user.email = :email")
    Object[] getJoinedTeamByEmail(@Param("email")String email);

    @Query("SELECT COUNT(j) FROM JoinTeam j WHERE j.user = :user AND j.team = :team")
    int isJoined(@Param("user")User user, @Param("team")Team team);

    @Query("SELECT j.grade FROM JoinTeam j WHERE j.user = :user AND j.team = :team")
    int isLeader(@Param("user")User user, @Param("team")Team team);


}
