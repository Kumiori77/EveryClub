package com.example.everyclub.service;

import com.example.everyclub.dto.UserDTO;
import com.example.everyclub.entity.User;
import com.example.everyclub.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Boolean checkUser(UserDTO userDTO) {


        return false;
    }

    // 회원가입 이메일 & 닉네임 중복 확인 메소드
    @Override
    public String checkOnly(UserDTO userDTO) {

        String email = userDTO.getEmail();
        String nickname = userDTO.getNickname();

        int checkEmail = userRepository.countEmail(email);
        int checkNickname = userRepository.countNickname(nickname);

        String result = "";

        if (checkEmail > 0) {
            result += "E";
        }
        if (checkNickname > 0) {
            result += "N";
        }

        return result;
    }

    // 비밀번호 확인에 똑같이 입력했는지 확인하는 메소드
    @Override
    public String checkPassword(UserDTO userDTO) {

        String p1 = userDTO.getPassword1();
        String p2 = userDTO.getPassword2();

        if (p1.equals(p2)){
            return "";
        }
        else {
            return "P";
        }
    }
}
