package com.example.everyclub.repository;

import com.example.everyclub.entity.Post;
import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Test
    public void testGetPosts() {

        Pageable pageable = PageRequest.of(1, 10, Sort.by("pno").descending());

        Page<Object> result = postRepository.getPosts(pageable);

        for (Object obj : result) {
            Post post = (Post) obj;
            System.out.println(post.getTitle());
        }

    }

}
