package com.example.everyclub.repository;

import com.example.everyclub.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void createTeam() {

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Team team = Team.builder()
                    .teamName("Team_" + i)
                    .likeTeam((long) 0)
                    .build();

            teamRepository.save(team);
        });

    }

}
