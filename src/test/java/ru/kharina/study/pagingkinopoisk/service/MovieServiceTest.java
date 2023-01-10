package ru.kharina.study.pagingkinopoisk.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.kharina.study.pagingkinopoisk.dto.CriticDto;
import ru.kharina.study.pagingkinopoisk.dto.GenreDto;
import ru.kharina.study.pagingkinopoisk.dto.MovieDto;
import ru.kharina.study.pagingkinopoisk.model.*;
import ru.kharina.study.pagingkinopoisk.repository.*;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private EntityManager entityManager;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CriticRepository criticRepository;
    @Mock
    private MovieCriteriaRepository movieCriteriaRepository;
    @Mock
    private MovieService movieService;
    @Mock
    private CriticCriteriaRepository criticCriteriaRepository;
    @Mock
    Movie movie;
    @Mock
    MovieDto movieDto;
    @Mock
    MoviePage moviePage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        movieService = new MovieService(movieRepository, movieCriteriaRepository, criticRepository);
        movie = new Movie(1,"name", "description", 120, 4, 2020, new ArrayList<>(), new ArrayList<>());
        movieDto = new MovieDto(movie.getId(), movie.getName(), movie.getDescription());
    }

    @Test
    void testGetMovies() {
    }

    @Test
    void testAddMovie() {
        mock(Movie.class);
        mock(MovieRepository.class);

        when(movieRepository.save(movie)).thenReturn(movie);
        assertThat(movieService.addMovie(movie)).isEqualTo(movie);
    }

    @Test
    void testUpdateMovie() {
        mock(Movie.class);
        mock(MovieRepository.class);

        when(movieRepository.save(movie)).thenReturn(movie);
        assertThat(movieService.addMovie(movie)).isEqualTo(movie);
    }

    @Test
    void testGetMovieById() {
        mock(Movie.class);
        mock(MovieDto.class);
        mock(MovieRepository.class);
        mock(MovieCriteriaRepository.class);

        when(movieCriteriaRepository.convertEntityToDto(movieRepository.getOne(movie.getId()))).thenReturn(movieDto);
        assertThat(movieService.getMovieById(1)).isEqualTo(movieDto);
    }

    @Test
    void testDeleteMovieById() {
        mock(Movie.class);
        mock(MovieRepository.class, Mockito.CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(movieRepository).deleteById(any());
        assertThat(movieService.deleteMovieById(1)).isEqualTo(true);
    }

    @Test
    void testGetAllMovie() {
        mock(Movie.class);
        mock(MovieRepository.class);

        moviePage = new MoviePage();

        MovieSearchCriteria movieSearchCriteria = new MovieSearchCriteria();

        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(movieDto);

        Page page = new PageImpl<>(movieDtos,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC,"name")),
                movieDtos.size());

        when(movieCriteriaRepository.findAllWithFilters(moviePage,movieSearchCriteria,"")).thenReturn(page);

        assertThat(movieService.getMovies(moviePage,movieSearchCriteria,"").getContent().get(0).getName()).isEqualTo(movie.getName());
    }
}