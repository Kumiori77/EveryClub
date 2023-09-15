package com.example.everyclub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"user", "Team"})
public class Join  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jno;
    private int grade;

    @ManyToOne
    private User user;

    @ManyToOne
    private Team team;

}
