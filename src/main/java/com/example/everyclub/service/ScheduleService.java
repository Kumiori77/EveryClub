package com.example.everyclub.service;

import com.example.everyclub.dto.ScheduleDTO;
import com.example.everyclub.dto.TeamDTO;
import com.example.everyclub.entity.Schedule;
import com.example.everyclub.entity.Team;

import java.util.List;

public interface ScheduleService {

    default ScheduleDTO entityToDTO(Schedule schedule) {
        ScheduleDTO dto = ScheduleDTO.builder()
                .sno(schedule.getSno())
                .title(schedule.getTitle())
                .date(schedule.getDate())
                .content(schedule.getContent())
                .tno(schedule.getTeam().getTno())
                .build();

        return dto;
    }

    default Schedule dtoToEntity(ScheduleDTO dto) {

        Team team = Team.builder()
                .tno(dto.getTno()).build();

        Schedule entity = Schedule.builder()
                .sno(dto.getSno())
                .title(dto.getTitle())
                .date(dto.getDate())
                .content(dto.getContent())
                .team(team).build();

        return entity;
    }

    List<ScheduleDTO> getScheduleList(TeamDTO teamDTO);

    void delete(Long sno);

    void update(ScheduleDTO scheduleDTO);

    void upload(ScheduleDTO scheduleDTO);
}
