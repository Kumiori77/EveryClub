package com.example.everyclub.dto;

import com.example.everyclub.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

    private Long sno;
    private String title;
    private LocalDateTime date;
    private String content;

}
