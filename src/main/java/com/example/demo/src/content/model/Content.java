package com.example.demo.src.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Content {
    private int contentId;
    private String conName;
    private String releseYear;
    private String screenTime;
    private String conPoster;
    private String conDescript;
    private String hits;
    private String recentHits;
    private String director;
    private String actor;
    private String writer;
    private String series;
    private String viewLimit;
    private String nation;
}
