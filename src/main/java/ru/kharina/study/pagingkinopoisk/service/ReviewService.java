package ru.kharina.study.pagingkinopoisk.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.kharina.study.pagingkinopoisk.dto.ReviewDto;
import ru.kharina.study.pagingkinopoisk.model.Movie;
import ru.kharina.study.pagingkinopoisk.model.Review;
import ru.kharina.study.pagingkinopoisk.model.ReviewPage;
import ru.kharina.study.pagingkinopoisk.repository.MovieRepository;
import ru.kharina.study.pagingkinopoisk.repository.ReviewCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.ReviewRepository;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewCriteriaRepository reviewCriteriaRepository;
    private final MovieRepository movieRepository;
    private final CriticService criticService;
    private final MovieService movieService;

    public ReviewService(ReviewRepository reviewRepository,
                         ReviewCriteriaRepository reviewCriteriaRepository,
                         MovieRepository movieRepository,
                         CriticService criticService,
                         MovieService movieService) {
        this.reviewRepository = reviewRepository;
        this.reviewCriteriaRepository = reviewCriteriaRepository;
        this.movieRepository = movieRepository;
        this.criticService = criticService;
        this.movieService = movieService;
    }

    public Page<ReviewDto> getReviewsDto(ReviewPage page){
        return reviewCriteriaRepository.findPageableDto(page);
    }

    public Review convertDtoToEntity(ReviewDto dto){
        Review review = new Review();
        review.setId(dto.getId());
        review.setDate(new Date());
        review.setMessage(dto.getMessage());
        review.setScore(dto.getScore());
        System.out.println(dto.getMovie());
        review.setMovie(movieService.preConvertDtoToEntity(dto.getMovie()));
        review.setCritic(criticService.convertDtoToEntity(dto.getCritic()));
        return review;
    }

    public ReviewDto addReviewDto(ReviewDto reviewDto) {
        reviewRepository.save(convertDtoToEntity(reviewDto));
        Movie movie = movieRepository.getOne(reviewDto.getMovie().getId());
        List<Review> reviewList = movie.getReviewList();
        int count = 0;
        for (Review review : reviewList) {
            count += review.getScore();
        }
        double score = (double)count/reviewList.size();
        movie.setScore(score);
        movieRepository.save(movie);
        return reviewDto;
    }

    public ReviewDto updateReview(ReviewDto newReview, int id) {
        ReviewDto result = getReviewById(id);
        result.setId(id);
        result.setScore(newReview.getScore());
        result.setMessage(newReview.getMessage());
        result.setCritic(newReview.getCritic());
        result.setMovie(newReview.getMovie());
        result.setDate(newReview.getDate());
        reviewRepository.save(convertDtoToEntity(result));
        return result;
    }

    //GET по id
    public ReviewDto getReviewById(int id) {
        return reviewCriteriaRepository.convertEntityToDto(reviewRepository.getOne(id));
    }

    //DELETE по id
    public boolean deleteReviewById(int id) {
        reviewRepository.deleteById(id);
        return true;
    }
}
