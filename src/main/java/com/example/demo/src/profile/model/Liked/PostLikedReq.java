package com.example.demo.src.profile.model.Liked;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLikedReq {
    private int profileId;
    private int userId;
    private String likeCont;
}
