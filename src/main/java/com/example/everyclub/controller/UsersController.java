package com.example.everyclub.controller;

import com.example.everyclub.dto.UserDTO;
import com.example.everyclub.service.UserService;
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
