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

        Object[] result = joinTeamRepository.getTeamByEmail(user.getEmail());

        for(Object entity : result) {
            System.out.println(entity);
        }
    }

    // 유저가 가입하지 않은 팀 목록 보기
    @Test
    public void getNotJoinedTeam() {
        User user = User.builder()
                .email("user_1@kopo.com").build();

        Object[] result = joinTeamRepository.getTeamWithoutEmail(user.getEmail());

        for(Object entity : result) {
            System.out.println(entity);
        }
    }

}
