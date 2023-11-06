package com.example.everyclub.repository;

import com.example.everyclub.entity.Schedule;
import com.example.everyclub.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s, Team  t WHERE s.team = :team ORDER BY s.date ASC ")
    List<Object> getScheduleByTeam(@Param("team")Team team) ;
}
