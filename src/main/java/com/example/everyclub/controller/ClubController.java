package com.example.everyclub.controller;

import com.example.everyclub.dto.*;
import com.example.everyclub.entity.Post;
import com.example.everyclub.repository.JoinTeamRepository;
import com.example.everyclub.service.PostService;
import com.example.everyclub.service.ScheduleService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/club")
@Log4j2
@RequiredArgsConstructor
public class ClubController {

    private final TeamService teamService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final JoinTeamRepository joinTeamRepository;
    private final PostService postService;

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
    public String team(@PathVariable("tno") Long tno, HttpServletRequest httpServletRequest,
                       Model model, PageRequestDTO pageRequestDTO) {

        // 로그인 여부 확인
        HttpSession session = httpServletRequest.getSession();
        String user = (String) session.getAttribute("user");

        if (user == null) {
            // 메인페이지로 이동해서 로그인 하지 않으면 해당 페이지에 접근하지 못하게 하기
            return "redirect:/club/main";
        }

        // 전달할 데이터
        UserDTO userDTO = userService.getUser(user);
        TeamDTO teamDTO = teamService.getTeamByTno(tno);
        List<ScheduleDTO> scheduleList = scheduleService.getScheduleList(teamDTO);
        PageResultDTO<PostDTO, Object[]> posts = postService.getList(pageRequestDTO, tno);

        // 모임 가입 여부
        int isJoined = joinTeamRepository.isJoined(userService.dtoToEntity(userDTO), teamService.dtoToEntity(teamDTO));
        switch (isJoined){
            case 0: model.addAttribute("isJoined", false);
            break;
            default:
                model.addAttribute("isJoined", true);

                // 모임장 여부
                int isLeader = joinTeamRepository.isLeader(userService.dtoToEntity(userDTO), teamService.dtoToEntity(teamDTO));
                if (isLeader == 1) {
                    model.addAttribute("isLeader", true);
                }
                else {
                    model.addAttribute("isLeader", false);
                }

                break;
        }

        // 모델에 메시지 담기
        model.addAttribute("user", userDTO);
        model.addAttribute("team", teamDTO);
        model.addAttribute("scheduleList", scheduleList);
        model.addAttribute("posts", posts);

        return "club/team";
    }

    @GetMapping("/register/{tno}")
    public String register(@PathVariable("tno") Long tno, HttpServletRequest httpServletRequest,
                           Model model) {

        // 로그인 여부 확인
        HttpSession session = httpServletRequest.getSession();
        String user = (String) session.getAttribute("user");

        if (user == null) {
            // 메인페이지로 이동해서 로그인 하지 않으면 해당 페이지에 접근하지 못하게 하기
            return "redirect:/club/main";
        }

        // 전달할 데이터
        UserDTO userDTO = userService.getUser(user);
        TeamDTO teamDTO = teamService.getTeamByTno(tno);

        // 모델에 메시지 담기
        model.addAttribute("user", userDTO);
        model.addAttribute("team", teamDTO);

        return "club/register";
    }

    @PostMapping("/register/{tno}")
    public String register(@PathVariable("tno") Long tno, PostDTO postDTO) {

        postService.register(postDTO);

        return "redirect:/club/team/"+tno;
    }

    @GetMapping("/read/{tno}")
    public String read(@PathVariable("tno") Long tno, Long pno, HttpServletRequest httpServletRequest,
                           Model model) {

        // 로그인 여부 확인
        HttpSession session = httpServletRequest.getSession();
        String user = (String) session.getAttribute("user");

        if (user == null) {
            // 메인페이지로 이동해서 로그인 하지 않으면 해당 페이지에 접근하지 못하게 하기
            return "redirect:/club/main";
        }

        // 전달할 데이터
        UserDTO userDTO = userService.getUser(user);
        TeamDTO teamDTO = teamService.getTeamByTno(tno);
        PostDTO postDTO = postService.getPostByPno(pno);


        // 모델에 메시지 담기
        model.addAttribute("user", userDTO);
        model.addAttribute("team", teamDTO);
        model.addAttribute("post", postDTO);

        return "club/read";
    }

}
