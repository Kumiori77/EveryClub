package com.example.everyclub.repository;

import com.example.everyclub.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void cretaeUser() {

        IntStream.rangeClosed(1, 10).forEach(i -> {
            User user = User.builder()
                    .email("user_"+i+"@kopo.com")
                    .password("0000")
                    .nickname("USER_"+i)
                    .build();

            userRepository.save(user);
        });
    }

    @Test
    public void checkCount() {
        String email = "user_1@kopo.com";
        String nickname = "USER_1";

        int a = userRepository.countEmail(email);
        int b = userRepository.countNickname(nickname);

        System.out.println("email : " + a + "\nnickname : " + b);
    }
}
