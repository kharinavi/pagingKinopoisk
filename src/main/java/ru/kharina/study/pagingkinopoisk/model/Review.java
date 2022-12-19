package ru.kharina.study.pagingkinopoisk.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "score")
    private int score;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private Date date;

    @OneToOne
    @JoinColumn(name = "critic_id")
    private Critic critic;

    @ManyToOne(optional=false)
    @JoinColumn(name = "movie_id")
    private Movie movie;
}

