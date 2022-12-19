package ru.kharina.study.pagingkinopoisk.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.kharina.study.pagingkinopoisk.dto.GenreDto;
import ru.kharina.study.pagingkinopoisk.model.Genre;
import ru.kharina.study.pagingkinopoisk.model.MoviePage;
import ru.kharina.study.pagingkinopoisk.repository.GenreCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.GenreRepository;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreCriteriaRepository genreCriteriaRepository;

    public GenreService(GenreRepository genreRepository,
                         GenreCriteriaRepository genreCriteriaRepository) {
        this.genreRepository = genreRepository;
        this.genreCriteriaRepository = genreCriteriaRepository;
    }

    public Page<GenreDto> getGenresDto(MoviePage page){
        return genreCriteriaRepository.findPageableDto(page);
    }

    private Genre convertDtoToEntity(GenreDto dto){
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        genre.setDescription(dto.getDescription());
        return genre;
    }

    public Genre addGenreDto(GenreDto genre) {
        return genreRepository.save(convertDtoToEntity(genre));
    }

    public GenreDto updateGenre(GenreDto newGenre, int id) {
        GenreDto result = getGenreById(id);
        result.setId(id);
        result.setName(newGenre.getName());
        result.setMovieList(newGenre.getMovieList());
        result.setDescription(newGenre.getDescription());
        genreRepository.save(convertDtoToEntity(result));
        return result;
    }

    //GET по id
    public GenreDto getGenreById(int id) {
        Integer idInt = id;
        return genreCriteriaRepository.convertEntityToDto(genreRepository.getOne(idInt));
    }

    //DELETE по id
    public void deleteGenreById(int id) {
        genreRepository.deleteById(id);
    }

}
