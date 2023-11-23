package com.example.everyclub.service;

import com.example.everyclub.dto.ScheduleDTO;
import com.example.everyclub.dto.TeamDTO;
import com.example.everyclub.entity.Schedule;
import com.example.everyclub.entity.Team;
import com.example.everyclub.repository.ScheduleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleDTO> getScheduleList(TeamDTO teamDTO) {

        List<Object> result = scheduleRepository.getScheduleByTeam(teamDTO.getTno());

        return result.stream().map(x -> entityToDTO((Schedule) x)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long sno) {

        scheduleRepository.deleteById(sno);

    }

    @Override
    public void upload(ScheduleDTO scheduleDTO) {
        Schedule schedule = dtoToEntity(scheduleDTO);
        scheduleRepository.save(schedule);
    }
}
