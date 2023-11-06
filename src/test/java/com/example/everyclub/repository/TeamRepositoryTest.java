package com.example.everyclub.repository;

import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
                    .description("This Team is very Good Team")
                    .likeTeam((long) 0)
                    .build();

            teamRepository.save(team);
        });

    }

    @Test
    public void getAllTeamTest() {

        List<Object> result = teamRepository.getAllTeams();

        for (Object team : result) {
            System.out.println(team);
        }

    }

    @Test
    public void testGetTeamsNotJoined() {
        User user = User.builder()
                .email("user_1@kopo.com").build();

        List<Object[]> result = teamRepository.getTeamNotJoined(user.getEmail());

        for(Object team[] :  result) {
            System.out.println(Arrays.toString(team));
        }
    }

    @Test
    public void testGetTeamByTno() {

        Optional<Team> team = teamRepository.findById(1L);

        System.out.println(team.get().getDescription());

    }

}
