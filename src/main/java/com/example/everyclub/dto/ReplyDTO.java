package com.example.everyclub.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno;
    private String text;
    private Long pno;
    private String replyer;
    private String replyerNickname;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;

}
