package com.example.everyclub.repository;

import com.example.everyclub.entity.Post;
import com.example.everyclub.entity.Reply;
import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoyTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void addReply() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            int j = (i % 10) +1;

            User user = User.builder()
                    .email("user_"+j+"@kopo.com").build();


            Post post = Post.builder()
                    .pno((long) i)
                    .build();

            Reply reply = Reply.builder()
                    .text("Hello..")
                    .post(post)
                    .replyer(user).build();


            replyRepository.save(reply);
        });

    }

    @Test
    public void testGetRepliesByPno(){

        List<Object> result = replyRepository.getRepliesByPno(100L);

        System.out.println(result.size());

        for (Object obj : result) {
            System.out.println(obj);
        }

    }

}
