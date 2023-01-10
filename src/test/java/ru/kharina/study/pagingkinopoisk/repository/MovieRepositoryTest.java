package ru.kharina.study.pagingkinopoisk.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.kharina.study.pagingkinopoisk.model.Movie;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;
    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie(1,"name", "description", 120, 4, 2020, new ArrayList<>(), new ArrayList<>());
        movieRepository.save(movie);
    }

    @AfterEach
    void tearDown() {
        movie = null;
        movieRepository.deleteAll();
    }

    @Test
    void testFindById_Found()
    {
        Movie movieFound = movieRepository.findById(1).get();
        assertThat(movieFound.getId()).isEqualTo(movie.getId());
        assertThat(movieFound.getName()).isEqualTo(movie.getName());
    }
}
