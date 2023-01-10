package ru.kharina.study.pagingkinopoisk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.kharina.study.pagingkinopoisk.dto.MovieDto;
import ru.kharina.study.pagingkinopoisk.model.Movie;
import ru.kharina.study.pagingkinopoisk.model.MoviePage;
import ru.kharina.study.pagingkinopoisk.model.MovieSearchCriteria;
import ru.kharina.study.pagingkinopoisk.service.MovieService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    MovieDto movieDtoOne;
    MovieDto movieDtoTwo;
    Movie movie;
    List<MovieDto> movieList = new ArrayList<>();

    @BeforeEach
    void init() {
        movie = new Movie(1,"name", "description", 120, 4, 2020, new ArrayList<>(), new ArrayList<>());
        movieDtoOne = new MovieDto(1, "name", "description");
        movieDtoTwo = new MovieDto(2, "name2", "description2");
        movieList.add(movieDtoOne);
        movieList.add(movieDtoTwo);
    }

    @Test
    void testGetMovieDtoById() throws Exception{
        when(movieService.getMovieById(1)).thenReturn(movieDtoOne);

        this.mockMvc.perform(get("/movie/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetMovies() throws Exception {

        MoviePage moviePage = new MoviePage();
        Page page = new PageImpl<>(movieList,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC,"name")),
                movieList.size());
        MovieSearchCriteria movieSearchCriteria = new MovieSearchCriteria();

        when(movieService.getMovies(moviePage, movieSearchCriteria, "")).thenReturn(page);

        this.mockMvc.perform(get("/movie"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testAddMovie() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(movieDtoOne);

        when(movieService.addMovie(movie)).thenReturn(movie);

        this.mockMvc.perform(post("/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateMovie() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(movieDtoTwo);

        when(movieService.updateMovie(movieDtoTwo,1)).thenReturn(movieDtoTwo);

        this.mockMvc.perform(put("/movie/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteMovie() throws Exception {

        when(movieService.deleteMovieById(1)).thenReturn(true);

        this.mockMvc.perform(delete("/movie/1"))
                .andDo(print()).andExpect(status().isOk());

    }
}
