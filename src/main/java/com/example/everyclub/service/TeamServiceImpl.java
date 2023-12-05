package com.example.everyclub.service;

import com.example.everyclub.dto.TeamDTO;
import com.example.everyclub.entity.Team;
import com.example.everyclub.repository.JoinTeamRepository;
import com.example.everyclub.repository.TeamRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        for (Object[] arr : result) {
            TeamDTO teamDTO = TeamDTO.builder()
                    .tno((Long) arr[0])
                    .teamName((String) arr[1])
                    .description((String) arr[2])
                    .likeTeam((Long) arr[3]).build();

            recommendTeams.add(teamDTO);
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

    @Override
    public TeamDTO getTeamByTno(Long tno) {
        Optional<Team> team = teamRepository.findById(tno);

        return entityToDTO(team.get());
    }

    @Override
    public void mkTeam(TeamDTO teamDTO) {
        teamRepository.save(dtoToEntity(teamDTO));
    }

    @Override
    @Transactional
    public void removeByTno(Long tno) {
        teamRepository.deleteById(tno);
    }
}
