package com.example.demo.src.profile.model.Eval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetEvalRes {
    private int evalContId;
    private int profileId;
    private int userId;
    private String evalCont;
    private Boolean score;
}
