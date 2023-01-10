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
import ru.kharina.study.pagingkinopoisk.dto.GenreDto;
import ru.kharina.study.pagingkinopoisk.model.Genre;
import ru.kharina.study.pagingkinopoisk.model.MoviePage;
import ru.kharina.study.pagingkinopoisk.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    GenreDto genreDtoOne;
    GenreDto genreDtoTwo;
    Genre genre;
    List<GenreDto> genreList = new ArrayList<>();

    @BeforeEach
    void init() {
        genre = new Genre(1, "name", "description");
        genreDtoOne = new GenreDto(1, "name", "description");
        genreDtoTwo = new GenreDto(2, "name2", "description2");
        genreList.add(genreDtoOne);
        genreList.add(genreDtoTwo);
    }

    @Test
    void testGetGenreDtoById() throws Exception{
        when(genreService.getGenreById(1)).thenReturn(genreDtoOne);

        this.mockMvc.perform(get("/genre/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetGenres() throws Exception {

        MoviePage genrePage = new MoviePage();
        Page page = new PageImpl<>(genreList,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC,"name")),
                genreList.size());

        when(genreService.getGenresDto(genrePage)).thenReturn(page);

        this.mockMvc.perform(get("/genre"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testAddGenre() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(genreDtoOne);

        when(genreService.addGenreDto(genreDtoOne)).thenReturn(genre);

        this.mockMvc.perform(post("/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateGenre() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(genreDtoTwo);

        when(genreService.updateGenre(genreDtoTwo,1)).thenReturn(genreDtoTwo);

        this.mockMvc.perform(put("/genre/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteGenre() throws Exception {

        when(genreService.deleteGenreById(1)).thenReturn(true);

        this.mockMvc.perform(delete("/genre/1"))
                .andDo(print()).andExpect(status().isOk());

    }
}
