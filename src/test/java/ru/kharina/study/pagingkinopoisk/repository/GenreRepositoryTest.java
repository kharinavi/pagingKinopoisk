package ru.kharina.study.pagingkinopoisk.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.kharina.study.pagingkinopoisk.model.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;
    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre(1, "genre", "xxxxx");
        genreRepository.save(genre);
    }

    @AfterEach
    void tearDown() {
        genre = null;
        genreRepository.deleteAll();
    }

    @Test
    void testFindById_Found()
    {
        Genre genreFound = genreRepository.findById(1).get();
        assertThat(genreFound.getId()).isEqualTo(genre.getId());
        assertThat(genreFound.getName()).isEqualTo(genre.getName());
    }
}
