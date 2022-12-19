package ru.kharina.study.pagingkinopoisk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kharina.study.pagingkinopoisk.dto.MovieDto;
import ru.kharina.study.pagingkinopoisk.dto.ReviewDto;
import ru.kharina.study.pagingkinopoisk.model.Review;
import ru.kharina.study.pagingkinopoisk.model.ReviewPage;
import ru.kharina.study.pagingkinopoisk.service.ReviewService;

@Api(value = "Контроллер для работы с рецензиями")
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ApiOperation(value = "Получение рецензии")
    @GetMapping
    public ResponseEntity<Page<ReviewDto>> getReviews(ReviewPage page){
        Page<ReviewDto> reviewPages = reviewService.getReviewsDto(page);
        return new ResponseEntity<>(reviewPages, HttpStatus.OK);
    }

    @ApiOperation(value = "Сохранение рецензии")
    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewDto review){
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
        return new ResponseEntity<>(reviewService.addReviewDto(review), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ReviewDto getReviewDtoById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }

    @PutMapping("/{id}")
    ReviewDto updateReview(@RequestBody ReviewDto newReview, @PathVariable int id) {
        return reviewService.updateReview(newReview, id);
    }

    @DeleteMapping("/{id}")
    void deleteReview(@PathVariable int id) {
        reviewService.deleteReviewById(id);
    }
}
