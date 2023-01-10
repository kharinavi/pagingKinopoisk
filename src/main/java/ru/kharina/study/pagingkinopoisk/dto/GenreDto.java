package ru.kharina.study.pagingkinopoisk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kharina.study.pagingkinopoisk.model.Movie;

import java.util.List;

@Schema(description = "Сущность жанра")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    @Schema(description = "Идентификатор в таблице genres")
    private int id;
    @Schema(description = "Название жанра")
    private String name;
    @Schema(description = "Описание жанра")
    private String description;
    @Schema(description = "Список фильмов этого жанра")
    private List<MovieDto> movieList;

    public GenreDto(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
