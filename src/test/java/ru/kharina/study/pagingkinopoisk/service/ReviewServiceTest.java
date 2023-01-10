package ru.kharina.study.pagingkinopoisk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.kharina.study.pagingkinopoisk.dto.ReviewDto;
import ru.kharina.study.pagingkinopoisk.model.Review;
import ru.kharina.study.pagingkinopoisk.model.ReviewPage;
import ru.kharina.study.pagingkinopoisk.repository.MovieRepository;
import ru.kharina.study.pagingkinopoisk.repository.ReviewCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewCriteriaRepository reviewCriteriaRepository;
    @Mock
    private ReviewService reviewService;
    @Mock
    MovieRepository movieRepository;
    @Mock
    MovieService movieService;
    @Mock
    CriticService criticService;
    @Mock
    Review review;
    @Mock
    ReviewDto reviewDto;
    @Mock
    ReviewPage reviewPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reviewService = new ReviewService(reviewRepository, reviewCriteriaRepository, movieRepository, criticService, movieService);
        review = new Review(1, 5, "message");
        reviewDto = new ReviewDto(review.getId(), review.getScore(), review.getMessage());
    }

    @Test
    void testGetReviewsDto() {
        mock(Review.class);
        mock(ReviewRepository.class);

        reviewPage = new ReviewPage();

        List<ReviewDto> reviewDtos = new ArrayList<>();
        reviewDtos.add(reviewDto);

        Page page = new PageImpl<>(reviewDtos,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC,"name")),
                reviewDtos.size());

        when(reviewCriteriaRepository.findPageableDto(reviewPage)).thenReturn(page);

        assertThat(reviewService.getReviewsDto(reviewPage).getContent().get(0).getMessage()).isEqualTo(review.getMessage());
    }

    @Test
    void testGetReviewById() {
        mock(Review.class);
        mock(ReviewDto.class);
        mock(ReviewRepository.class);
        mock(ReviewCriteriaRepository.class);

        reviewDto = reviewCriteriaRepository.convertEntityToDto(review);

        when(reviewCriteriaRepository.convertEntityToDto(reviewRepository.getOne(review.getId()))).thenReturn(reviewDto);
        assertThat(reviewService.getReviewById(1)).isEqualTo(reviewDto);
    }

    @Test
    void testDeleteReviewById() {
        mock(Review.class);
        mock(ReviewRepository.class, Mockito.CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(reviewRepository).deleteById(any());
        assertThat(reviewService.deleteReviewById(1)).isEqualTo(true);
    }
}