package com.example.everyclub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class User extends BaseEntity {

    @Id
    private String email;
    private String password;
    private String nickname;

}
