package ru.kharina.study.pagingkinopoisk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.kharina.study.pagingkinopoisk.dto.ReviewDto;
import ru.kharina.study.pagingkinopoisk.model.Review;
import ru.kharina.study.pagingkinopoisk.model.ReviewPage;
import ru.kharina.study.pagingkinopoisk.service.ReviewService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    ReviewDto reviewDtoOne;
    ReviewDto reviewDtoTwo;
    Review review;
    List<ReviewDto> reviewList = new ArrayList<>();

    @BeforeEach
    void init() {
        review = new Review(1, 5, "message");
        reviewDtoOne = new ReviewDto(1, 5, "message");
        reviewDtoTwo = new ReviewDto(2, 4, "message2");
        reviewList.add(reviewDtoOne);
        reviewList.add(reviewDtoTwo);
    }

    @Test
    void testGetReviewDtoById() throws Exception{
        when(reviewService.getReviewById(1)).thenReturn(reviewDtoOne);

        this.mockMvc.perform(get("/review/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetReviews() throws Exception {

        ReviewPage reviewPage = new ReviewPage();
        Page page = new PageImpl<>(reviewList,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC,"message")),
                reviewList.size());

        when(reviewService.getReviewsDto(reviewPage)).thenReturn(page);

        this.mockMvc.perform(get("/review"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testAddReview() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(reviewDtoOne);

        when(reviewService.addReviewDto(reviewDtoOne)).thenReturn(reviewDtoOne);

        this.mockMvc.perform(post("/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateReview() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(reviewDtoTwo);

        when(reviewService.updateReview(reviewDtoTwo,1)).thenReturn(reviewDtoTwo);

        this.mockMvc.perform(put("/review/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteReview() throws Exception {

        when(reviewService.deleteReviewById(1)).thenReturn(true);

        this.mockMvc.perform(delete("/review/1"))
                .andDo(print()).andExpect(status().isOk());

    }
}