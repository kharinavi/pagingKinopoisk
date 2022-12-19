package ru.kharina.study.pagingkinopoisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kharina.study.pagingkinopoisk.model.Review;

public interface ReviewRepository  extends JpaRepository<Review, Integer> {
}
