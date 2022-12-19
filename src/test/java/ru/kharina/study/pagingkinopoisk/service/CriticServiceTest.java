package ru.kharina.study.pagingkinopoisk.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kharina.study.pagingkinopoisk.model.Critic;
import ru.kharina.study.pagingkinopoisk.repository.CriticCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.CriticRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CriticServiceTest {

    @Mock
    private CriticRepository criticRepository;
    private CriticCriteriaRepository criticCriteriaRepository;
    private CriticService criticService;
    AutoCloseable autoCloseable;
    Critic critic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        criticService = new CriticService(criticRepository, criticCriteriaRepository);
        critic = new Critic("firstName", "lastName", "description");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addCritic() {
        mock(Critic.class);
        mock(CriticRepository.class);
        mock(CriticCriteriaRepository.class);

        when(criticRepository.save(critic)).thenReturn(critic);
        assertThat(criticService.addCritic(critic)).isEqualTo(critic);
    }
}
