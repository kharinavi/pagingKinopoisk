package ru.kharina.study.pagingkinopoisk.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.kharina.study.pagingkinopoisk.model.Critic;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CriticRepositoryTest {

        @Autowired
        private CriticRepository criticRepository;
        private Critic critic;

        @BeforeEach
        void setUp() {
            critic = new Critic("critic", "critic", "xxxxx");
            criticRepository.save(critic);
        }

        @AfterEach
        void tearDown() {
            critic = null;
            criticRepository.deleteAll();
        }

        @Test
        void testFindById_Found()
        {
            Critic criticFound = criticRepository.findById(1).get();
            assertThat(criticFound.getId()).isEqualTo(critic.getId());
            assertThat(criticFound.getLastName()).isEqualTo(critic.getLastName());
        }
}
