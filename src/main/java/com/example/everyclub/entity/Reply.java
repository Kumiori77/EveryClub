package com.example.everyclub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Controller;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"post", "replyer"})
@Controller
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User replyer;


}
