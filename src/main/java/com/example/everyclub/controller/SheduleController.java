package com.example.everyclub.controller;

import com.example.everyclub.dto.ScheduleDTO;
import com.example.everyclub.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/schedule")
@Log4j2
@RequiredArgsConstructor
public class SheduleController {

    final private ScheduleService scheduleService;

    @GetMapping("/modify")
    public String modyfy(@RequestParam("title")String title, @RequestParam("sno")Long sno, @RequestParam("tno")Long tno,
                         @RequestParam("date")String date, @RequestParam("content")String content) {

        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime localDateTime = localDate.atTime(LocalTime.MIN);


        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                .sno(sno)
                .title(title)
                .content(content)
                .date(localDateTime)
                .tno(tno).build();

//        System.out.println(scheduleDTO.getTitle());
        scheduleService.update(scheduleDTO);

        return "redirect:/club/team/" + scheduleDTO.getTno();
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("sno")Long sno, @RequestParam("tno")Long tno) {

        scheduleService.delete(sno);

        return "redirect:/club/team/" + tno;
    }

}
