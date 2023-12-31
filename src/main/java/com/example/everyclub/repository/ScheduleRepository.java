package com.example.everyclub.repository;

import com.example.everyclub.entity.Schedule;
import com.example.everyclub.entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.team.tno = :tno")
    List<Object> getScheduleByTeam(@Param("tno")Long tno) ;

    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.team.tno = :tno")
    void deleteByTno(@Param("tno")Long tno);
}
