package ru.kharina.study.pagingkinopoisk.repository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import ru.kharina.study.pagingkinopoisk.dto.GenreDto;
import ru.kharina.study.pagingkinopoisk.dto.MovieDto;
import ru.kharina.study.pagingkinopoisk.model.Genre;
import ru.kharina.study.pagingkinopoisk.model.Movie;
import ru.kharina.study.pagingkinopoisk.model.MoviePage;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GenreCriteriaRepository {
    private final GenreRepository genreRepository;
    private final MovieCriteriaRepository movieCriteriaRepository;

    public GenreCriteriaRepository(GenreRepository genreRepository, MovieCriteriaRepository movieCriteriaRepository) {
        this.genreRepository = genreRepository;
        this.movieCriteriaRepository = movieCriteriaRepository;
    }

    List<MovieDto> getDtoMovieList(List<Movie> movies) {
        return movies
                .stream()
                .map(movieCriteriaRepository::preConvertEntityToDto)
                .collect(Collectors.toList());
    }

    public GenreDto convertEntityToDto(Genre genre){
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        genreDto.setDescription(genre.getDescription());
        genreDto.setMovieList(getDtoMovieList(genre.getMovieList()));
        return genreDto;
    }

    public Page<GenreDto> findPageableDto(MoviePage page){
        Pageable pageable = getPageable(page);
        List<GenreDto> dtoList = genreRepository.findAll(pageable)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList,pageable,dtoList.size());
    }

    private Pageable getPageable(MoviePage page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }
}
