package com.example.everyclub.repository;

import com.example.everyclub.entity.Schedule;
import com.example.everyclub.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ScheduleRepositoyTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    public void addSchedule() {
        IntStream.rangeClosed(1, 100).forEach(i ->{

            int j = (i % 10) + 1;
            int k = (i / 10) + 1;

            Team team = Team.builder()
                    .tno((long)j).build();

            Schedule schedule = Schedule.builder()
                    .title("Sample Schedule.."+k)
                    .date(LocalDateTime.now().plusDays((long)k))
                    .content("Sampel.."+k)
                    .team(team)
                    .build();

            scheduleRepository.save(schedule);
        });
    }

    @Test
    public void testGetScheduleByTeam() {

        Pageable pageable = PageRequest.of(1, 5, Sort.by("sno").descending());

        List<Object> result = scheduleRepository.getScheduleByTeam(pageable, 1L);

        for (Object obj : result) {
            System.out.println(obj);
        }

    }

}
