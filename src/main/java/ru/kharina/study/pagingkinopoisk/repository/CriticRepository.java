package ru.kharina.study.pagingkinopoisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kharina.study.pagingkinopoisk.model.Critic;

@Repository
public interface CriticRepository  extends JpaRepository<Critic, Integer> {
}
