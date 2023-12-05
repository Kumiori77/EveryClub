package com.example.everyclub.repository;

import com.example.everyclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT COUNT(*) FROM User WHERE email = :email")
    int countEmail(@Param("email") String email);

    @Query("SELECT COUNT(*) FROM User WHERE nickname = :nickname")
    int countNickname(@Param("nickname") String nickname);

    @Query("SELECT COUNT(*) FROM User WHERE email = :email AND password = :password")
    int checkUser(@Param("email")String email, @Param("password")String password);

    User getUserByEmail(String email);

    // 가입한 유저 목록
    @Query("SELECT u FROM User u LEFT JOIN JoinTeam j ON j.user = u " +
            "WHERE j.team.tno = :tno AND j.grade=0")
    List<Object> getJoinedUsers(@Param("tno") Long tno);


}
