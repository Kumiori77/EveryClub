package com.example.everyclub.service;

import com.example.everyclub.dto.PageRequestDTO;
import com.example.everyclub.dto.PageResultDTO;
import com.example.everyclub.dto.PostDTO;
import com.example.everyclub.entity.Post;
import com.example.everyclub.entity.User;
import com.example.everyclub.repository.PostRepository;
import com.example.everyclub.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    @Override
    public PageResultDTO<PostDTO, Object[]> getList(PageRequestDTO pageRequestDTO, Long tno) {

        Function<Object[], PostDTO> fn = (en -> entityToDTO((Post) en[0],(User) en[1],(Long) en[2]));

        Page<Object[]> result = postRepository.getPosts(
                pageRequestDTO.getPageable(Sort.by("pno").descending()), tno);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public void register(PostDTO postDTO) {

        Post post = dtoToEntity(postDTO);

        postRepository.save(post);

    }

    @Override
    public PostDTO getPostByPno(Long pno) {

        Object[] result = (Object[]) postRepository.getPostByPno(pno);

        PostDTO postDTO = entityToDTO((Post) result[0], (User) result[1], 0L);

        return postDTO;
    }

    @Transactional
    @Override
    public void remove(Long pno) {
        replyRepository.deleteByPno(pno);
        postRepository.deleteById(pno);
    }
}
