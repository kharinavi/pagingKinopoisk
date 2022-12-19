package ru.kharina.study.pagingkinopoisk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ru.kharina.study.pagingkinopoisk.dto.GenreDto;
import ru.kharina.study.pagingkinopoisk.dto.MovieDto;
import ru.kharina.study.pagingkinopoisk.model.Movie;
import ru.kharina.study.pagingkinopoisk.model.MoviePage;
import ru.kharina.study.pagingkinopoisk.model.MovieSearchCriteria;
import ru.kharina.study.pagingkinopoisk.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/movie")
@Api(value = "Контроллер для работы с фильмами")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @ApiOperation(value = "Получение кино")
    @GetMapping
    public ResponseEntity<Page<MovieDto>> getMovies(@RequestParam(name = "genre", required = false) String genre,
                                                    MoviePage page, MovieSearchCriteria movieSearchCriteria){
        return new ResponseEntity<>(movieService.getMovies(page, movieSearchCriteria, genre),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Сохранить кино", position = 1)
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    MovieDto getGenreDtoById(@PathVariable int id) {
        return movieService.getMovieById(id);
    }

    @PutMapping("/{id}")
    MovieDto updateMovie(@RequestBody MovieDto newMovie, @PathVariable int id) {
        return movieService.updateMovie(newMovie, id);
    }

    @DeleteMapping("/{id}")
    void deleteMovie(@PathVariable int id) {
        movieService.deleteMovieById(id);
    }
}
