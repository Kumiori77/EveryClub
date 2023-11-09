package com.example.everyclub.controller;

import com.example.everyclub.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
@Log4j2
@RequiredArgsConstructor
public class SheduleController {

    final private ScheduleService scheduleService;

    @GetMapping("/modify")
    public String modyfy() {
        return "redirect:/club/team/1";
    }

}
