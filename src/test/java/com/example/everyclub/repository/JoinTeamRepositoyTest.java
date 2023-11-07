package com.example.everyclub.repository;

import com.example.everyclub.entity.JoinTeam;
import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
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

            // 처음 팀 생성
//            Team team = Team.builder()
//                    .tno((long) i).build();
//
//            JoinTeam joinTeam = JoinTeam.builder()
//                    .user(user)
//                    .team(team)
//                    .grade(1)
//                    .build();
            
            // 다른 팀에 가입
            Team team = Team.builder()
                    .tno((long) (11 - i)).build();

            JoinTeam joinTeam = JoinTeam.builder()
                    .user(user)
                    .team(team)
                    .grade(0)
                    .build();

            joinTeamRepository.save(joinTeam);
        });

    }

    // 유저가 가입한 팀 목록 보기
    @Test
    public void getJoinedTeam() {

        User user = User.builder()
                .email("user_1@kopo.com").build();

        Object[] result = joinTeamRepository.getJoinedTeamByEmail(user.getEmail());

        for(Object entity : result) {
            System.out.println(entity);
        }
    }

    @Test
    public void testIsJoined() {

        User user1 = User.builder()
                .email("user_1@kopo.com").build();
        User user2 = User.builder()
                .email("user_2@kopo.com").build();

        Team team = Team.builder()
                .tno(1L).build();

        System.out.println(joinTeamRepository.isJoined(user1, team));
        System.out.println(joinTeamRepository.isJoined(user2, team));

    }

    @Test
    public void testIsLeader() {

        User user = User.builder()
                .email("user_1@kopo.com").build();

        Team team1 = Team.builder()
                .tno(1L).build();
        Team team2 = Team.builder()
                .tno(10L).build();

        System.out.println(joinTeamRepository.isLeader(user, team1));
        System.out.println(joinTeamRepository.isLeader(user, team2));
    }

}
