package com.example.everyclub.controller;

import com.example.everyclub.dto.UserDTO;
import com.example.everyclub.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users/")
@Log4j2
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/login")
    public void login() {

    }

    @PostMapping("/login")
    public String doLogin(UserDTO userDTO, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {

        if (userService.login(userDTO)) {
            // 로그인 성공
            // 새션 가져오기
            HttpSession session = httpServletRequest.getSession();

            // 세션에 유저 추가
            session.setAttribute("user", userDTO);

            // 세션 유효시간 지정 (30분)
            session.setMaxInactiveInterval(1800);

            // 매인화면으로 리다이렉트
            return "redirect:/club/main";
        }
        else {
            redirectAttributes.addFlashAttribute("msg", "login_fail");
            return "redirect:login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        session.invalidate(); // 세션 파기

        return "redirect:/club/main";
    }

    @GetMapping("/signup")
    public void signup() {
    }

    @PostMapping("/signup")
    public String signup(UserDTO userDTO, RedirectAttributes redirectAttributes) {

        String check = userService.checkOnly(userDTO);

        check += userService.checkPassword(userDTO);

        log.info("CHECK : " + check);

        if (check.equals("")) {
            // 아무 문제 없으면 회원 가입 진행
            userService.signup(userDTO);

            // 회원가입 되었음을 알림
            redirectAttributes.addFlashAttribute("msg", "signup");

            // 로그인 화면으로 리다이렉트
            return "redirect:login";
        }
        // 에러 내용을 포함해서 다시 표시
        redirectAttributes.addFlashAttribute("msg", check);
        redirectAttributes.addFlashAttribute("dto", userDTO);

        return "redirect:signup";
    }

}
