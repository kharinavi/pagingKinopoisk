package ru.kharina.study.pagingkinopoisk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kharina.study.pagingkinopoisk.dto.GenreDto;
import ru.kharina.study.pagingkinopoisk.model.Genre;
import ru.kharina.study.pagingkinopoisk.model.MoviePage;
import ru.kharina.study.pagingkinopoisk.service.GenreService;

@Api(value = "Контроллер для работы с жанрами")
@RestController
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @ApiOperation(value = "Получение жанра")
    @GetMapping
    public ResponseEntity<Page<GenreDto>> getGenres(MoviePage page){
        Page<GenreDto> genrePages = genreService.getGenresDto(page);
        return new ResponseEntity<>(genrePages, HttpStatus.OK);
    }

    @ApiOperation(value = "Сохранение жанра")
    @PostMapping
    public ResponseEntity<Genre> addGenre(@RequestBody GenreDto genre){
        return new ResponseEntity<>(genreService.addGenreDto(genre), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    GenreDto getGenreDtoById(@PathVariable int id) {
        return genreService.getGenreById(id);
    }

    @PutMapping("/{id}")
    GenreDto updateGenre(@RequestBody GenreDto newGenre, @PathVariable int id) {
        return genreService.updateGenre(newGenre, id);
    }

    @DeleteMapping("/{id}")
    void deleteGenre(@PathVariable int id) {
        genreService.deleteGenreById(id);
    }
}
