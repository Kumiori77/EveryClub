package com.example.everyclub.service;

import com.example.everyclub.dto.ReplyDTO;
import com.example.everyclub.entity.Reply;
import com.example.everyclub.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    @Override
    public List<ReplyDTO> getReplyList(Long pno) {

        List<Object> result = replyRepository.getRepliesByPno(pno);

        return result.stream().map(x -> entityToDto((Reply) x)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReplyDTO replyDTO) {

        Reply reply = dtoToEntity(replyDTO);

        replyRepository.save(reply);

        return reply.getRno();

    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
