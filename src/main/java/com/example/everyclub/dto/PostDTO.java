package com.example.everyclub.dto;

import com.example.everyclub.entity.Team;
import com.example.everyclub.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long pno;
    private String title;
    private String content;
    private Long tno;
    private String writer;
    private String nickname;
    private int replyCnt;
}
