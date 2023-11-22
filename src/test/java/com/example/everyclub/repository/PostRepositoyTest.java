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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Test
    public void testGetPosts() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
        Team team = Team.builder().tno(1L).build();

        Page<Object[]> result = postRepository.getPosts(pageable, 1L);
        System.out.println("_________________________________");

        for (Object obj[]: result) {
            User user = (User) obj[1];
            Post post = (Post) obj[0];
            Long rplCnt = (Long) obj[2];
            System.out.println(post.getTitle());
            System.out.println(user.getEmail());
            System.out.println(rplCnt);
//            System.out.println(obj.toString());
            System.out.println("______________________");
        }

    }

}
