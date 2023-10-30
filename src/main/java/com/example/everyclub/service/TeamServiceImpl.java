package com.example.everyclub.service;

import com.example.everyclub.dto.TeamDTO;
import com.example.everyclub.entity.Team;
import com.example.everyclub.repository.JoinTeamRepository;
import com.example.everyclub.repository.TeamRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private JoinTeamRepository joinTeamRepository;


    // 가입한 팀 목록 반환
    @Override
    public List<TeamDTO> getJoinedTeams(String email) {
        Object[] result = joinTeamRepository.getJoinedTeamByEmail(email);

        List<TeamDTO> joinedTeams = new ArrayList<>();

        for (Object team : result) {
            joinedTeams.add(entityToDTO((Team) team));
        }
        return joinedTeams;
    }

    // 추천 팀 목록 반환
    @Override
    public List<TeamDTO> getRecommendTeamsForUser(String email) {
        List<Object[]> result = teamRepository.getTeamNotJoined(email);

        List<TeamDTO> recommendTeams = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Object[] obj = result.get(i);

            Team team = Team.builder()
                    .tno((Long) obj[0])
                    .teamName((String) obj[1])
                    .likeTeam((Long) obj[2]).build();

            recommendTeams.add(entityToDTO(team));
        }

        return recommendTeams;
    }


    // 모든 팀 목록 반환
    @Override
    public List<TeamDTO> getSRecommendTeamsForAnyone() {
        List<Object> result = teamRepository.getAllTeams();

        List<TeamDTO> allTeams = new ArrayList<>();

        for (int i=0; i < 3; i++) {
            allTeams.add(entityToDTO((Team) result.get(i)));
        }

        return allTeams;
    }
}
