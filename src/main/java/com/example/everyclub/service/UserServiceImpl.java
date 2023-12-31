package com.example.everyclub.service;

import com.example.everyclub.dto.UserDTO;
import com.example.everyclub.entity.User;
import com.example.everyclub.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Boolean login(UserDTO userDTO) {

        String email = userDTO.getEmail();
        String password = userDTO.getPassword();

        // 유저 확인
        int cnt = userRepository.checkUser(email, password);


        if (cnt == 1) {
            //로그인 성공
            return true;
        }
        else {
            // 로그인 실패
            return false;
        }
    }

    // 로그인 한 유저 DTO 반환
    @Override
    public UserDTO getUser(String email) {

        User entity = userRepository.getUserByEmail(email);

        UserDTO user = entityToDTO(entity);

        return user;
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

        String p1 = userDTO.getPassword();
        String p2 = userDTO.getPassword2();

        if (p1.equals(p2)){
            return "";
        }
        else {
            return "P";
        }
    }

    // 회원가입
    @Override
    public void signup(UserDTO userDTO) {
        User entity = dtoToEntity(userDTO);

        userRepository.save(entity);
    }

    // 가입한 유저 목록 받아오기
    @Override
    public List<UserDTO> getUserList(long tno) {

        List<Object> result = userRepository.getJoinedUsers(tno);

        return result.stream().map(x -> entityToDTO((User) x)).collect(Collectors.toList());
    }
}
