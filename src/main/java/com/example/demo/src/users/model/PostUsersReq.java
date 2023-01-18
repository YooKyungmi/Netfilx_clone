package com.example.demo.src.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUsersReq {
    private String email;
    private String membership;
    private String password;
    private String phoneNum;
}
