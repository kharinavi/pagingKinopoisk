package ru.kharina.study.pagingkinopoisk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kharina.study.pagingkinopoisk.dto.CriticDto;
import ru.kharina.study.pagingkinopoisk.model.Critic;
import ru.kharina.study.pagingkinopoisk.model.CriticPage;
import ru.kharina.study.pagingkinopoisk.repository.CriticCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.CriticRepository;
import ru.kharina.study.pagingkinopoisk.service.CriticService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CriticController.class)
public class CriticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CriticService criticService;

    CriticDto criticDtoOne;
    CriticDto criticDtoTwo;
    Critic critic;
    List<CriticDto> criticList = new ArrayList<>();

    @BeforeEach
    void init() {
        critic = new Critic(1,"firstName","lastName","description");
        criticDtoOne = new CriticDto(1,"firstName","lastName","description");
        criticDtoTwo = new CriticDto(2,"firstName2","lastName2","description2");
        criticList.add(criticDtoOne);
        criticList.add(criticDtoTwo);
    }

    @Test
    void testGetCriticDtoById() throws Exception{
        when(criticService.getCriticById(1)).thenReturn(criticDtoOne);

        this.mockMvc.perform(get("/critic/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetCritics() throws Exception {

        CriticPage criticPage = new CriticPage();
        Page page = new PageImpl<>(criticList,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC,"lastName")),
                criticList.size());

        when(criticService.getCritics(criticPage)).thenReturn(page);

        this.mockMvc.perform(get("/critic"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testAddCritic() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(criticDtoOne);

        when(criticService.addCriticDto(criticDtoOne)).thenReturn(critic);

        this.mockMvc.perform(post("/critic")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateCritic() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(criticDtoTwo);

        when(criticService.updateCritic(criticDtoTwo,1)).thenReturn(criticDtoTwo);

        this.mockMvc.perform(put("/critic/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCritic() throws Exception {

        when(criticService.deleteCriticById(1)).thenReturn(true);

        this.mockMvc.perform(delete("/critic/1"))
                .andDo(print()).andExpect(status().isOk());

    }
}
