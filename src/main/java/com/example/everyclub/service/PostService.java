package com.example.everyclub.service;

import com.example.everyclub.dto.PageRequestDTO;
import com.example.everyclub.dto.PageResultDTO;
import com.example.everyclub.dto.PostDTO;
import com.example.everyclub.entity.Post;
import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;

public interface PostService {

    default Post dtoToEntity(PostDTO postDTO) {

        Team team = Team.builder().tno(postDTO.getTno()).build();
        User user = User.builder().email(postDTO.getWriter()).build();

        Post post = Post.builder()
                .pno(postDTO.getPno())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .team(team)
                .writer(user).build();
        return post;
    }

    default PostDTO entityToDTO(Post post) {
        PostDTO postDTO = PostDTO.builder()
                .pno(post.getPno())
                .title(post.getTitle())
                .content(post.getContent())
                .tno(post.getTeam().getTno())
                .writer(post.getWriter().getEmail()).build();
        return postDTO;
    }

    PageResultDTO<PostDTO, Post> getList(PageRequestDTO pageRequestDTO);
}
