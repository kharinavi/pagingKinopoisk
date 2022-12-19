package ru.kharina.study.pagingkinopoisk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kharina.study.pagingkinopoisk.model.Critic;
import ru.kharina.study.pagingkinopoisk.model.CriticPage;
import ru.kharina.study.pagingkinopoisk.repository.CriticCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.CriticRepository;
import ru.kharina.study.pagingkinopoisk.service.CriticService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CriticControllerTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CriticRepository mockRepository;
    @MockBean
    private CriticCriteriaRepository mockCriteriaRepository;
    @MockBean
    private CriticService mockService;

    @Before
    public void init() {
        CriticPage criticPage = new CriticPage();
        //when(mockRepository.findById(1)).thenReturn(Optional.of(critic));
    }

    @Test
    public void find_allCritic_OK() throws Exception {

        /*CriticPage criticPage = new CriticPage();
        List<Critic> critics = Arrays.asList(
                new Critic(1,"Book A", "Ah Pig", "description1"),
                new Critic(2, "Book B", "Ah Dog", "description2"));
        Sort sort = Sort.by(criticPage.getSortDirection(), criticPage.getSortBy());
        Pageable pageable = PageRequest.of(criticPage.getPageNumber(), criticPage.getPageSize(), sort);
        Page<Critic> pageCritics = new PageImpl<Critic>(critics, pageable, 2);;

        when(mockService.getCritics(criticPage)).thenReturn(pageCritics);

        mockMvc.perform(get("/critic"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).findAll();*/
    }

    @Test
    public void save_critic_OK() throws Exception {

        Critic newCritic = new Critic(1,"Spring Boot Guide", "mkyong", "description");
        when(mockRepository.save(any(Critic.class))).thenReturn(newCritic);

        mockMvc.perform(post("/critic")
                        .content(om.writeValueAsString(newCritic))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot Guide")))
                .andExpect(jsonPath("$.author", is("mkyong")))
                .andExpect(jsonPath("$.price", is("description")));

        verify(mockRepository, times(1)).save(any(Critic.class));

    }
}
