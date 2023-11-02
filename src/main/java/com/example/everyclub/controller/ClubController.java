package com.example.everyclub.controller;

import com.example.everyclub.dto.TeamDTO;
import com.example.everyclub.dto.UserDTO;
import com.example.everyclub.service.TeamService;
import com.example.everyclub.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/club")
@Log4j2
@RequiredArgsConstructor
public class ClubController {

    private final TeamService teamService;
    private final UserService userService;

    @GetMapping("/main")
    public void mainPage(HttpServletRequest httpServletRequest, Model model) {

        // 로그인 여부 확인
        HttpSession session = httpServletRequest.getSession();
        String user = (String) session.getAttribute("user");

        if (user != null) {
            System.out.println("로그인 됨");

            // 전달할 데이터
            List<TeamDTO> recommendTeam = teamService.getRecommendTeamsForUser(user);
            List<TeamDTO> myTeam = teamService.getJoinedTeams(user);
            UserDTO userDTO = userService.getUser(user);

            // 모델에 메시지 담기
            model.addAttribute("user", userDTO);
            model.addAttribute("isUser", "true");
            model.addAttribute("recommendTeam", recommendTeam);
            model.addAttribute("myTeam", myTeam);
        }
        else {
            System.out.println("로그인 안됨");

            // 전달할 데이터
            List<TeamDTO> recommendTeam = teamService.getSRecommendTeamsForAnyone();

            // 모델에 메시지 담기
            model.addAttribute("isUser", "false");
            model.addAttribute("recommendTeam", recommendTeam);
        }


    }

    @GetMapping("/team/{tno}")
    public String team(@PathVariable("tno") Long tno) {

        return "team";
    }

//    @GetMapping("/team")
//    public void team() {
//
//   }
}
