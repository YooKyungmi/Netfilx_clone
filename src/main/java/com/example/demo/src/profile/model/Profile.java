package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Profile {
    private int profileId;
    private String viewLimit;
    private String userNick;
    private String proPicture;
    private String language;
}
