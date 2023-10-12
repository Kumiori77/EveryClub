package com.example.everyclub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"team", "writer"})
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;


}
