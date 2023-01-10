package ru.kharina.study.pagingkinopoisk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.kharina.study.pagingkinopoisk.dto.GenreDto;
import ru.kharina.study.pagingkinopoisk.model.Genre;
import ru.kharina.study.pagingkinopoisk.model.MoviePage;
import ru.kharina.study.pagingkinopoisk.repository.GenreCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;
    @Mock
    private GenreCriteriaRepository genreCriteriaRepository;
    @Mock
    private GenreService genreService;
    @Mock
    Genre genre;
    @Mock
    GenreDto genreDto;
    @Mock
    MoviePage genrePage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        genreService = new GenreService(genreRepository, genreCriteriaRepository);
        genre = new Genre(1, "name", "description");
        genreDto = new GenreDto(genre.getId(), genre.getName(), genre.getDescription());
    }

    @Test
    void testAddGenreDto() {
        mock(Genre.class);
        mock(GenreRepository.class);

        when(genreRepository.save(genre)).thenReturn(genre);
        assertThat(genreService.addGenreDto(genreDto)).isEqualTo(genre);
    }

    @Test
    void testUpdateGenre() {
        mock(Genre.class);
        mock(GenreRepository.class);

        when(genreRepository.save(genre)).thenReturn(genre);
        assertThat(genreService.addGenreDto(genreDto)).isEqualTo(genre);
    }

    @Test
    void testDeleteGenreById() {
        mock(Genre.class);
        mock(GenreRepository.class, Mockito.CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(genreRepository).deleteById(any());
        assertThat(genreService.deleteGenreById(1)).isEqualTo(true);
    }

    @Test
    void getGenresDto() {
        mock(Genre.class);
        mock(GenreRepository.class);

        genrePage = new MoviePage();

        List<GenreDto> genreDtos = new ArrayList<>();
        genreDtos.add(genreDto);

        Page page = new PageImpl<>(genreDtos,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC,"name")),
                genreDtos.size());

        when(genreCriteriaRepository.findPageableDto(genrePage)).thenReturn(page);

        assertThat(genreService.getGenresDto(genrePage).getContent().get(0).getName()).isEqualTo(genre.getName());
    }

    @Test
    void testGetGenreById() {
        mock(Genre.class);
        mock(GenreDto.class);
        mock(GenreRepository.class);
        mock(GenreCriteriaRepository.class);

        genreDto = genreCriteriaRepository.convertEntityToDto(genre);

        when(genreCriteriaRepository.convertEntityToDto(genreRepository.getOne(genre.getId()))).thenReturn(genreDto);
        assertThat(genreService.getGenreById(1)).isEqualTo(genreDto);
    }
}