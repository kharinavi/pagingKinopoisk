package ru.kharina.study.pagingkinopoisk.model;

import lombok.Data;

import java.util.List;

@Data
public class MovieSearchCriteria {
    private String name;
    private String description;
    private int duration;
    private double score;
    private int releaseYear;
    private List<Genre> genreList;
}
