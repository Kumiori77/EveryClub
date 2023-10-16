package com.example.everyclub.service;

import com.example.everyclub.dto.UserDTO;
import com.example.everyclub.entity.User;

public interface UserService {

    default User dtoToEntity(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword1())
                .nickname(userDTO.getNickname())
                .build();

        return user;
    }

    default UserDTO entityToDTO(User user) {
        UserDTO dto = UserDTO.builder()
                .email(user.getEmail())
                .password1(user.getPassword())
                .nickname(user.getNickname()).build();

        return dto;
    }

    // 로그인 추상 메소드
    Boolean checkUser(UserDTO userDTO);

    // 회원가입 이메일 & 닉네임 중복 확인 메소드
    String checkOnly(UserDTO userDTO);

    // 비밀번호 확인에 똑같이 입력했는지 확인하는 메소드
    String checkPassword(UserDTO userDTO);


}
