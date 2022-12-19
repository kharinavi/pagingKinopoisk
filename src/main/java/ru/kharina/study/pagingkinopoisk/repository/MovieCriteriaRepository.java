package ru.kharina.study.pagingkinopoisk.repository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import ru.kharina.study.pagingkinopoisk.dto.MovieDto;
import ru.kharina.study.pagingkinopoisk.dto.ReviewDto;
import ru.kharina.study.pagingkinopoisk.model.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class MovieCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final MovieRepository movieRepository;
    private final CriticCriteriaRepository criticCriteriaRepository;

    public MovieCriteriaRepository(EntityManager entityManager, MovieRepository movieRepository, CriticCriteriaRepository criticCriteriaRepository) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.movieRepository = movieRepository;
        this.criticCriteriaRepository = criticCriteriaRepository;
    }

    public Page<Movie> findAllEntityWithFilters(MoviePage page,
                                          MovieSearchCriteria movieSearchCriteria){
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> movieRoot = criteriaQuery.from(Movie.class);
        Predicate predicate = getPredicate(movieSearchCriteria, movieRoot);
        criteriaQuery.where(predicate);
        setOrder(page, criteriaQuery, movieRoot);
        TypedQuery<Movie> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());
        Pageable pageable = getPageable(page);
        long moviesCount = getMoviesCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, moviesCount);
    }

    private List<ReviewDto> convertReviewEntityToDto(List<Review> reviewList) {
        List<ReviewDto> result = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setScore(review.getScore());
            reviewDto.setDate(review.getDate());
            reviewDto.setMessage(review.getMessage());
            reviewDto.setCritic(criticCriteriaRepository.convertEntityToDto(review.getCritic()));
            result.add(reviewDto);
        }
        return result;
    }

    public MovieDto preConvertEntityToDto(Movie movie){
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setName(movie.getName());
        movieDto.setScore(movie.getScore());
        movieDto.setDuration(movie.getDuration());
        movieDto.setDescription(movie.getDescription());
        movieDto.setReleaseYear(movie.getReleaseYear());
        movieDto.setDuration(movie.getDuration());
        return movieDto;
    }

    public MovieDto convertEntityToDto(Movie movie){
        MovieDto movieDto = preConvertEntityToDto(movie);
        movieDto.setReviewList(convertReviewEntityToDto(movie.getReviewList()));
        return movieDto;
    }

    public Page<MovieDto> findAllWithFilters(MoviePage page,
                                             MovieSearchCriteria movieSearchCriteria,
                                             String genre) {
        Page<Movie> moviePages = findAllEntityWithFilters(page, movieSearchCriteria);
        if (genre != null) {
            List<Movie> movieList = new ArrayList<>();
            List<Movie> genreMovieList= findAllEntityByGenre(genre);
            if (!moviePages.isEmpty()) {
                List<Movie> otherFiltersMovies = moviePages.getContent();
                for(int i = 0; i < otherFiltersMovies.size(); i++) {
                    for (int j = 0; j < genreMovieList.size(); j++) {
                        if (otherFiltersMovies.get(i).getId() == genreMovieList.get(j).getId())
                            movieList.add(otherFiltersMovies.get(i));
                    }
                }
            }
            moviePages = new PageImpl<>(movieList, getPageable(page), movieList.size());
        }
        Page<MovieDto> movieDtoPages = moviePages.map(this::convertEntityToDto);
        return movieDtoPages;
    }

    private List<Movie> findAllEntityByGenre(String genreS) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movieRepository.findAll()) {
            for (Genre genre : movie.getGenreList()) {
                if (genre.getName().equals(genreS)) {
                    result.add(movie);
                }
            }
        }
        return result;
    }

    private Predicate getPredicate(MovieSearchCriteria movieSearchCriteria,
                                   Root<Movie> movieRoot) {
        List<Predicate> predicates = new ArrayList<>();
        System.out.println(predicates.size());
        System.out.println(movieSearchCriteria.getName());
        System.out.println(movieSearchCriteria.getDescription());
        System.out.println(movieSearchCriteria.getDuration());
        System.out.println(movieSearchCriteria.getReleaseYear());
        System.out.println(movieSearchCriteria.getScore());
        if(Objects.nonNull(movieSearchCriteria.getName())){
            System.out.println("score!! "+movieSearchCriteria.getName());
            predicates.add(
                    criteriaBuilder.like(movieRoot.get("name"),
                            "%" + movieSearchCriteria.getName() + "%")
            );
        }
        if(Objects.nonNull(movieSearchCriteria.getDescription())){
            System.out.println("descritpion!! "+movieSearchCriteria.getDescription());
            predicates.add(
                    criteriaBuilder.like(movieRoot.get("description"),
                            "%" + movieSearchCriteria.getDescription() + "%")
            );
        }
        if(movieSearchCriteria.getDuration() != 0){
            String duration = Integer.toString(movieSearchCriteria.getDuration());
            System.out.println("duration!! "+duration);
            predicates.add(
                    criteriaBuilder.equal(movieRoot.get("duration"),
                             duration )
            );
        }
        if(movieSearchCriteria.getReleaseYear() != 0){
            String releaseYear = Integer.toString(movieSearchCriteria.getReleaseYear());
            System.out.println("releaseYear!! "+releaseYear);
            predicates.add(
                    criteriaBuilder.equal(movieRoot.get("releaseYear"),
                             releaseYear )
            );
        }
        if(movieSearchCriteria.getScore() != 0){
            String score = Double.toString(movieSearchCriteria.getScore());
            System.out.println("score!! "+score);
            predicates.add(
                    criteriaBuilder.equal(movieRoot.get("score"),
                             score )
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(MoviePage page,
                          CriteriaQuery<Movie> criteriaQuery,
                          Root<Movie> movieRoot) {
        if(page.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(movieRoot.get(page.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(movieRoot.get(page.getSortBy())));
        }
    }

    private Pageable getPageable(MoviePage page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }

    private long getMoviesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Movie> countRoot = countQuery.from(Movie.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
