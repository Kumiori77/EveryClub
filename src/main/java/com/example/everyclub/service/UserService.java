package com.example.everyclub.service;

import com.example.everyclub.dto.UserDTO;
import com.example.everyclub.entity.User;

public interface UserService {

    default User dtoToEntity(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .nickname(userDTO.getNickname())
                .build();

        return user;
    }

    default UserDTO entityToDTO(User user) {
        UserDTO dto = UserDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname()).build();

        return dto;
    }

    // 로그인 추상 메소드
    Boolean login(UserDTO userDTO);

    // 로그인한 유저 정보 반환
    UserDTO getUser(String email);

    // 회원가입 이메일 & 닉네임 중복 확인 메소드
    String checkOnly(UserDTO userDTO);

    // 비밀번호 확인에 똑같이 입력했는지 확인하는 메소드
    String checkPassword(UserDTO userDTO);

    // 회원가입 메소드
    void signup(UserDTO userDTO);


}
