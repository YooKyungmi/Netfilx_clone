package com.example.demo.src.profile.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProfileRes {
    private int profileId;
    private String viewLimit;
    private String userNick;
    private String proPicture;
    private String language;
}
