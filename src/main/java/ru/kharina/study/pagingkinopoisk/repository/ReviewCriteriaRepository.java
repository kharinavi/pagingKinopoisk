package ru.kharina.study.pagingkinopoisk.repository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import ru.kharina.study.pagingkinopoisk.dto.MovieDto;
import ru.kharina.study.pagingkinopoisk.dto.ReviewDto;
import ru.kharina.study.pagingkinopoisk.model.Movie;
import ru.kharina.study.pagingkinopoisk.model.Review;
import ru.kharina.study.pagingkinopoisk.model.ReviewPage;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReviewCriteriaRepository {
    private final ReviewRepository reviewRepository;
    private final CriticCriteriaRepository criticCriteriaRepository;
    private final MovieCriteriaRepository movieCriteriaRepository;

    public ReviewCriteriaRepository(ReviewRepository reviewRepository,
                                    CriticCriteriaRepository criticCriteriaRepository,
                                    MovieCriteriaRepository movieCriteriaRepository) {
        this.reviewRepository = reviewRepository;
        this.criticCriteriaRepository = criticCriteriaRepository;
        this.movieCriteriaRepository = movieCriteriaRepository;
    }

    public ReviewDto convertEntityToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setScore(review.getScore());
        reviewDto.setDate(review.getDate());
        reviewDto.setMessage(review.getMessage());
        reviewDto.setMovie(movieCriteriaRepository.preConvertEntityToDto(review.getMovie()));
        reviewDto.setCritic(criticCriteriaRepository.convertEntityToDto(review.getCritic()));
        return reviewDto;
    }

    public Page<ReviewDto> findPageableDto(ReviewPage page){
        Pageable pageable = getPageable(page);
        List<ReviewDto> dtoList = reviewRepository.findAll(pageable)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList,pageable,dtoList.size());
    }

    private Pageable getPageable(ReviewPage page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }
}

