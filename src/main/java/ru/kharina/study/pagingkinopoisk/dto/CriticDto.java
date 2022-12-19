package ru.kharina.study.pagingkinopoisk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriticDto {
    private int id;
    private String firstName;
    private String lastName;
    private String description;
}
