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
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
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

        Pageable pageable = PageRequest.of(3, 5, Sort.by("pno").descending());

        Page<Object[]> result = postRepository.getPosts(pageable, 1L);
        System.out.println("_________________________________");
        int i = 0;

        for (Object obj[]: result) {
            i++;
            User user = (User) obj[1];
            Post post = (Post) obj[0];
            Long rplCnt = (Long) obj[2];
            System.out.println(post.getTitle());
            System.out.println(user.getEmail());
            System.out.println(rplCnt);
            System.out.println("______________________");
        }

        System.out.println("+++++++++++++");
        System.out.println(result.getTotalPages());
        System.out.println("+++++++++++++");
        System.out.println(i);

    }

    @Test
    public void testGetPostByPno() {

        Object result = postRepository.getPostByPno(100L);

        Object[] arr = (Object[])result;

        System.out.println("__________________");
        System.out.println(Arrays.toString(arr));

    }

    @Transactional
    @Commit
    @Test
    public void testRemoveBytno(){
        postRepository.removeByTno(10L);
    }

}
