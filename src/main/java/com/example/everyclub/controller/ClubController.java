package com.example.everyclub.controller;

import com.example.everyclub.dto.*;
import com.example.everyclub.entity.JoinTeam;
import com.example.everyclub.entity.Post;
import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;
import com.example.everyclub.repository.JoinTeamRepository;
import com.example.everyclub.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    private final ReplyService replyService;

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
                           PageRequestDTO pageRequestDTO, Model model) {

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
        List<ReplyDTO> replyDTOList = replyService.getReplyList(pno);
        postDTO.setReplyCnt(replyDTOList.size());

        // 모델에 메시지 담기
        model.addAttribute("user", userDTO);
        model.addAttribute("team", teamDTO);
        model.addAttribute("post", postDTO);
        model.addAttribute("replyList", replyDTOList);
        model.addAttribute("pageRequest", pageRequestDTO);

        return "club/read";
    }

    @GetMapping("/removePost/{tno}")
    public String removePost(@PathVariable("tno") Long tno, Long pno, PageRequestDTO pageRequestDTO) {
        postService.remove(pno);

        return "redirect:/club/team/" + tno + "?page=" + pageRequestDTO.getPage();
    }

    @GetMapping("/modifyPost/{tno}")
    public String modifyPost(@PathVariable("tno") Long tno, Long pno, HttpServletRequest httpServletRequest,
                       PageRequestDTO pageRequestDTO, Model model) {

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
        model.addAttribute("pageRequest", pageRequestDTO);

        return "club/modifyPost";
    }

    @PostMapping("/modifyPost/{tno}")
    public String modifyPost(@PathVariable("tno") Long tno, PostDTO postDTO, PageRequestDTO pageRequestDTO) {

        postService.register(postDTO);

        return "redirect:/club/read/"+tno+"?pno="+postDTO.getPno()+"&page="+pageRequestDTO.getPage();
    }

    // 모임 가입
    @PostMapping("/submit")
    public String submit(Long tno, String email){

        Team team = Team.builder().tno(tno).build();
        User user = User.builder().email(email).build();

        JoinTeam joinTeam = JoinTeam.builder()
                .grade(0)
                .team(team)
                .user(user).build();

        joinTeamRepository.save(joinTeam);

        return "redirect:/club/team/"+tno;
    }

    // 모임 탈퇴
    @PostMapping("/withDraw")
    @Transactional
    public String withDraw(Long tno, String email){

        replyService.removeByTnoReplyer(tno, email);
        postService.removeByTnoWriter(tno, email);
        joinTeamRepository.deleteByTnoEmail(tno, email);

        return "redirect:/club/team/"+tno;
    }


    // 팀 세팅
    @GetMapping("/setting/{tno}")
    public String setting(@PathVariable("tno")Long tno, Boolean isLeader, PageRequestDTO pageRequestDTO,
                          HttpServletRequest httpServletRequest, Model model) {

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
        List<UserDTO> joinedUsers = userService.getUserList(tno);

        model.addAttribute("user", userDTO);
        model.addAttribute("team", teamDTO);
        model.addAttribute("joinedUsers", joinedUsers);
        model.addAttribute("isLeader", isLeader);
        model.addAttribute("page", pageRequestDTO.getPage());

        return "club/setting";
    }

    @PostMapping("/setting/{tno}")
    public String setting(@PathVariable("tno") Long tno, TeamDTO teamDTO,
                          boolean isLeader, PageRequestDTO pageRequestDTO) {

        System.out.println(teamDTO.getTeamName());

        teamService.mkTeam(teamDTO);

        return "redirect:/club/setting/"+tno+"?isLeader="+isLeader+"&page="+pageRequestDTO.getPage();
    }

    @PostMapping("/removeTeam/{tno}")
    @Transactional
    public String removeTeam(@PathVariable("tno")Long tno){

        replyService.removeByTno(tno);
        postService.removeByTno(tno);
        scheduleService.removeByTno(tno);
        joinTeamRepository.deleteByTno(tno);
        teamService.removeByTno(tno);

        return "redirect:/club/main";
    }

    // 팀 생성
    @GetMapping("/makeTeam")
    public String makeTeam(HttpServletRequest httpServletRequest, Model model) {

        // 로그인 여부 확인
        HttpSession session = httpServletRequest.getSession();
        String user = (String) session.getAttribute("user");

        if (user == null) {
            // 메인페이지로 이동해서 로그인 하지 않으면 해당 페이지에 접근하지 못하게 하기
            return "redirect:/club/main";
        }

        UserDTO userDTO = userService.getUser(user);
        model.addAttribute("user", userDTO);

        return "club/makeTeam";
    }

    @PostMapping("/makeTeam")
    public String maekTeam(TeamDTO teamDTO, String email) {

        teamDTO.setLikeTeam(0L);
        teamService.mkTeam(teamDTO);

        // 모임을 생성한 유저를 리더로 등록
        Team team = Team.builder().tno(teamService.getTnoByTeamName(teamDTO.getTeamName())).build();
        User user = User.builder().email(email).build();

        JoinTeam joinTeam = JoinTeam.builder()
                .grade(1)
                .team(team)
                .user(user).build();

        joinTeamRepository.save(joinTeam);

        return "redirect:/club/team/" + (Long) teamService.getTnoByTeamName(teamDTO.getTeamName());
    }

    // 팀 검색
    @GetMapping("/searchTeam")
    public String searchTeam(HttpServletRequest httpServletRequest, String keyword, Model model) {

        // 로그인 여부 확인
        HttpSession session = httpServletRequest.getSession();
        String user = (String) session.getAttribute("user");

        if (user == null) {
            // 메인페이지로 이동해서 로그인 하지 않으면 해당 페이지에 접근하지 못하게 하기
            return "redirect:/club/main";
        }

        UserDTO userDTO = userService.getUser(user);
        model.addAttribute("user", userDTO);

        if (keyword != null) {
            List<TeamDTO> teams = teamService.getSearchTeam(keyword);
            model.addAttribute("teams", teams);
            model.addAttribute("keyword", keyword);
        }

        return "club/searchTeam";
    }


}
