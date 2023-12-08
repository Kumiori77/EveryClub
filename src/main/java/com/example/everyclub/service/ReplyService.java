package com.example.everyclub.service;

import com.example.everyclub.dto.ReplyDTO;
import com.example.everyclub.entity.Post;
import com.example.everyclub.entity.Reply;
import com.example.everyclub.entity.User;

import java.util.List;

public interface ReplyService {

    default ReplyDTO entityToDto(Reply reply) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .pno(reply.getPost().getPno())
                .replyer(reply.getReplyer().getEmail())
                .replyerNickname(reply.getReplyer().getNickname())
                .reg_date(reply.getReg_date())
                .mod_date(reply.getMod_date()).build();

        return replyDTO;
    }

    default Reply dtoToEntity(ReplyDTO replyDTO) {

        Post post = Post.builder().pno(replyDTO.getPno()).build();
        User user = User.builder().email(replyDTO.getReplyer()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .post(post)
                .replyer(user)
                .build();

        return reply;
    }

    List<ReplyDTO> getReplyList(Long pno);

    Long register (ReplyDTO replyDTO);

    void remove (Long rno);

    void removeByTno(Long tno);

    void removeByTnoReplyer(Long tno, String email);

}
