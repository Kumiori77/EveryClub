package com.example.everyclub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;
    @Column(unique = true)
    private String teamName;
    private String description;
    private Long likeTeam;

}
