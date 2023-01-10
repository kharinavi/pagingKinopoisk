package ru.kharina.study.pagingkinopoisk.repository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import ru.kharina.study.pagingkinopoisk.dto.CriticDto;
import ru.kharina.study.pagingkinopoisk.dto.MovieDto;
import ru.kharina.study.pagingkinopoisk.model.Critic;
import ru.kharina.study.pagingkinopoisk.model.CriticPage;
import ru.kharina.study.pagingkinopoisk.model.Movie;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CriticCriteriaRepository {
    private final CriticRepository criticRepository;

    public CriticCriteriaRepository(CriticRepository criticRepository) {
        this.criticRepository = criticRepository;
    }

    public CriticDto convertEntityToDto(Critic critic){
        CriticDto criticDto = new CriticDto();
        criticDto.setId(critic.getId());
        criticDto.setFirstName(critic.getFirstName());
        criticDto.setLastName(critic.getLastName());
        criticDto.setDescription(critic.getDescription());
        return criticDto;
    }

    public Page<CriticDto> findPageableDto(CriticPage page){
        Pageable pageable = getPageable(page);
        List<CriticDto> dtoList = criticRepository.findAll(pageable)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList,pageable,dtoList.size());
    }

    public Pageable getPageable(CriticPage page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }
}
