package ru.kharina.study.pagingkinopoisk.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private int id;
    private int score;
    private String message;
    private Date date;
    private CriticDto critic;
    private MovieDto movie;

    public ReviewDto(int id, int score, String message) {
        this.id = id;
        this.score = score;
        this.message = message;
    }
}
