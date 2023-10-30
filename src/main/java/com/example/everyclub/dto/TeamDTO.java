package com.example.everyclub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Long tno;
    private String teamName;
    private Long likeTeam;
}
