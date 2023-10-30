package com.example.everyclub.service;

import com.example.everyclub.dto.TeamDTO;
import com.example.everyclub.entity.Team;

import java.util.List;

public interface TeamService {

    default TeamDTO entityToDTO(Team team) {
        TeamDTO dto = TeamDTO.builder()
                .tno(team.getTno())
                .teamName(team.getTeamName())
                .likeTeam(team.getLikeTeam()).build();

        return dto;
    }

    default Team dtoToEntity(TeamDTO teamDTO) {
        Team entity = Team.builder()
                .tno(teamDTO.getTno())
                .teamName(teamDTO.getTeamName())
                .likeTeam(teamDTO.getLikeTeam()).build();

        return entity;
    }

    // 가입한 팀 목록
    List<TeamDTO> getJoinedTeams(String email);

    // 추천 팀 목록 (회원)
    List<TeamDTO> getRecommendTeamsForUser(String email);

    // 추천 팀 목록 (비회원)
    List<TeamDTO> getSRecommendTeamsForAnyone();
}
