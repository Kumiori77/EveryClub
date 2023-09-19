package com.example.everyclub.repository;

import com.example.everyclub.entity.JoinTeam;
import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class JoinTeamRepositoyTest {

    @Autowired
    private JoinTeamRepository joinTeamRepository;

    @Test
    public void joinTest() {

        IntStream.rangeClosed(1, 10).forEach(i -> {

            User user = User.builder()
                    .email("user_"+i+"@kopo.com").build();

            Team team = Team.builder()
                    .tno((long) i).build();

            JoinTeam joinTeam = JoinTeam.builder()
                    .user(user)
                    .team(team)
                    .grade(1)
                    .build();

            joinTeamRepository.save(joinTeam);
        });

    }

}
