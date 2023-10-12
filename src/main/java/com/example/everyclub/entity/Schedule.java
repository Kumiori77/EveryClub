package com.example.everyclub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "team")
public class Schedule extends BaseEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;
    private String title;
    private LocalDateTime date;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

}
