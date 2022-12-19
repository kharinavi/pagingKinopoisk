package ru.kharina.study.pagingkinopoisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kharina.study.pagingkinopoisk.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
