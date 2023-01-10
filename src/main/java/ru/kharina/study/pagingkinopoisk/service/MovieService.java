package ru.kharina.study.pagingkinopoisk.service;

import org.springframework.stereotype.Service;
import ru.kharina.study.pagingkinopoisk.dto.MovieDto;
import ru.kharina.study.pagingkinopoisk.dto.ReviewDto;
import ru.kharina.study.pagingkinopoisk.model.*;
import ru.kharina.study.pagingkinopoisk.repository.CriticRepository;
import ru.kharina.study.pagingkinopoisk.repository.MovieCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieCriteriaRepository movieCriteriaRepository;
    private final CriticRepository criticRepository;

    public MovieService(MovieRepository movieRepository,
                        MovieCriteriaRepository movieCriteriaRepository, CriticRepository criticRepository) {
        this.movieRepository = movieRepository;
        this.movieCriteriaRepository = movieCriteriaRepository;
        this.criticRepository = criticRepository;
    }

    public org.springframework.data.domain.Page<MovieDto> getMovies(MoviePage page,
                                                                 MovieSearchCriteria movieSearchCriteria,
                                                                 String movie){
        return movieCriteriaRepository.findAllWithFilters(page, movieSearchCriteria,movie);
    }

    public ru.kharina.study.pagingkinopoisk.model.Movie addMovie(ru.kharina.study.pagingkinopoisk.model.Movie movie){
        return movieRepository.save(movie);
    }

    private List<Review> convertReviewDtoToEntity(List<ReviewDto> reviewList) {
        List<Review> result = new ArrayList<>();
        for (ReviewDto dto : reviewList) {
            Review review = new Review();
            review.setScore(dto.getScore());
            review.setDate(dto.getDate());
            review.setMessage(dto.getMessage());
            review.setCritic(criticRepository.getOne(dto.getCritic().getId()));
            result.add(review);
        }
        return result;
    }

    public Movie preConvertDtoToEntity(MovieDto dto){
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setName(dto.getName());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setScore(dto.getScore());
        movie.setDuration(dto.getDuration());
        movie.setDescription(dto.getDescription());
        return movie;
    }

    private Movie convertDtoToEntity(MovieDto dto){
        Movie movie = preConvertDtoToEntity(dto);
        movie.setReviewList(convertReviewDtoToEntity(dto.getReviewList()));
        return movie;
    }

    public MovieDto updateMovie(MovieDto newMovie, int id) {
        MovieDto result = getMovieById(id);
        result.setId(id);
        result.setName(newMovie.getName());
        result.setScore(newMovie.getScore());
        result.setDuration(newMovie.getDuration());
        result.setReleaseYear(newMovie.getReleaseYear());
        result.setDescription(newMovie.getDescription());
        if (newMovie.getReviewList().isEmpty())
            result.setReviewList(new ArrayList<>());
        else
            result.setReviewList(newMovie.getReviewList());
        movieRepository.save(convertDtoToEntity(result));
        return result;
    }

    //GET по id
    public MovieDto getMovieById(int id) {
        return movieCriteriaRepository.convertEntityToDto(movieRepository.getOne(id));
    }

    //DELETE по id
    public boolean deleteMovieById(int id) {
        movieRepository.deleteById(id);
        return true;
    }
}
