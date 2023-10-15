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
    Boolean checkUser(UserDTO userDTO);

}
