package ru.kharina.study.pagingkinopoisk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kharina.study.pagingkinopoisk.model.Genre;

import java.util.List;

@Schema(description = "Сущность кино")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    @Schema(description = "Идентификатор в таблице movies")
    private int id;
    @Schema(description = "Название фильма")
    private String name;
    @Schema(description = "Описание фильма")
    private String description;
    @Schema(description = "Длительность фильма в минутах")
    private int duration;
    @Schema(description = "Оценка, максимум 5 баллов")
    private double score;
    @Schema(description = "Год выхода")
    private int releaseYear;
    @Schema(description = "Рецензии")
    /*private List<GenreDto> genreList;*/
    private List<ReviewDto> reviewList;
}
