package com.example.everyclub.controller;

import com.example.everyclub.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/club/")
@Log4j2
@RequiredArgsConstructor
public class ClubController {

    @GetMapping("/main")
    public void mainPage(HttpServletRequest httpServletRequest, Model model) {

        // 로그인 여부 확인
        HttpSession session = httpServletRequest.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if (userDTO != null) {
            System.out.println("로그인 됨");

            // 모델에 메시지 담기
            model.addAttribute("isUser", "true");
        }
        else {
            System.out.println("로그인 안됨");

            // 모델에 메시지 담기
            model.addAttribute("isUser", "false");
        }


    }

}
