package com.example.everyclub.repository;

import com.example.everyclub.entity.Post;
import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class PostRepositoyTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void addPost() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            int j = (i % 10) +1;

            User user = User.builder()
                    .email("user_"+j+"@kopo.com").build();

            Team team = Team.builder()
                    .tno((long) j).build();

            Post post = Post.builder()
                    .title("Title..."+i)
                    .content("Content......"+i)
                    .team(team)
                    .writer(user)
                    .build();

            postRepository.save(post);
        });

    }

}
