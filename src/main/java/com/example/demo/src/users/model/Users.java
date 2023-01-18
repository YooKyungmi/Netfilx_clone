package com.example.demo.src.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Users {
    private int userId;
    private String email;
    private String membership;
    private String password;
    private String phoneNum;
}
