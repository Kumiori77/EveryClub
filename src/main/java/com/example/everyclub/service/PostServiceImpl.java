package com.example.everyclub.service;

import com.example.everyclub.dto.PageRequestDTO;
import com.example.everyclub.dto.PageResultDTO;
import com.example.everyclub.dto.PostDTO;
import com.example.everyclub.entity.Post;
import com.example.everyclub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PageResultDTO<PostDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        Function<Object[], PostDTO> fn = (en -> entityToDTO(en));

        Page<Object[]> result = postRepository.getPosts(
                pageRequestDTO.getPageable(Sort.by("bno").descending()));


        return new PageResultDTO<>(result, fn);
    }
}
