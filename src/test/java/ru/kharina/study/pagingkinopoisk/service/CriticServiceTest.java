package ru.kharina.study.pagingkinopoisk.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.kharina.study.pagingkinopoisk.dto.CriticDto;
import ru.kharina.study.pagingkinopoisk.model.Critic;
import ru.kharina.study.pagingkinopoisk.model.CriticPage;
import ru.kharina.study.pagingkinopoisk.repository.CriticCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.CriticRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriticServiceTest {

    @Mock
    private CriticRepository criticRepository;
    @Mock
    private CriticCriteriaRepository criticCriteriaRepository;
    @Mock
    private CriticService criticService;
    @Mock
    Critic critic;
    @Mock
    CriticDto criticDto;
    @Mock
    CriticPage criticPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        criticService = new CriticService(criticRepository, criticCriteriaRepository);
        critic = new Critic(1,"firstName", "lastName", "description");
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testAddCritic() {
        mock(Critic.class);
        mock(CriticRepository.class);

        when(criticRepository.save(critic)).thenReturn(critic);
        assertThat(criticService.addCritic(critic)).isEqualTo(critic);
    }

    @Test
    void testUpdateCritic() {
        mock(Critic.class);
        mock(CriticRepository.class);

        when(criticRepository.save(critic)).thenReturn(critic);
        assertThat(criticService.addCritic(critic)).isEqualTo(critic);
    }

    @Test
    void testGetCriticById(){
        mock(Critic.class);
        mock(CriticDto.class);
        mock(CriticRepository.class);
        mock(CriticCriteriaRepository.class);

        criticDto = criticCriteriaRepository.convertEntityToDto(critic);

        when(criticCriteriaRepository.convertEntityToDto(criticRepository.getOne(critic.getId()))).thenReturn(criticDto);
        assertThat(criticService.getCriticById(1)).isEqualTo(criticDto);
    }

    @Test
    void testDeleteCriticById() {
        mock(Critic.class);
        mock(CriticRepository.class, Mockito.CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(criticRepository).deleteById(any());
        assertThat(criticService.deleteCriticById(1)).isEqualTo(true);
    }

    @Test
    void testGetAllCritic() {
        mock(Critic.class);
        mock(CriticRepository.class);

        criticPage = new CriticPage();

        criticDto = new CriticDto(critic.getId(),critic.getFirstName(),critic.getLastName(),critic.getDescription());

        List<CriticDto> criticDtos = new ArrayList<>();
        criticDtos.add(criticDto);

        Page page = new PageImpl<>(criticDtos,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC,"lastName")),
                criticDtos.size());

        when(criticCriteriaRepository.findPageableDto(criticPage)).thenReturn(page);

        assertThat(criticService.getCritics(criticPage).getContent().get(0).getFirstName()).isEqualTo(critic.getFirstName());
    }

}
