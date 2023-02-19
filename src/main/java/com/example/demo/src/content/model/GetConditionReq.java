package com.example.demo.src.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetConditionReq {
    private String conName = null;
    private String releseYear= null;
    private String director= null;
    private String actor= null;
    private String writer= null;
    private String series= null;
    private String viewLimit= null;
    private String nation= null;

}
