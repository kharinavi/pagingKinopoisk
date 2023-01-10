package ru.kharina.study.pagingkinopoisk.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.kharina.study.pagingkinopoisk.dto.CriticDto;
import ru.kharina.study.pagingkinopoisk.model.Critic;
import ru.kharina.study.pagingkinopoisk.model.CriticPage;
import ru.kharina.study.pagingkinopoisk.repository.CriticCriteriaRepository;
import ru.kharina.study.pagingkinopoisk.repository.CriticRepository;

@Service
public class CriticService {
    private final CriticRepository criticRepository;
    private final CriticCriteriaRepository criticCriteriaRepository;

    public CriticService(CriticRepository criticRepository,
                        CriticCriteriaRepository criticCriteriaRepository) {
        this.criticRepository = criticRepository;
        this.criticCriteriaRepository = criticCriteriaRepository;
    }

    public Page<CriticDto> getCritics(CriticPage page) {
        return criticCriteriaRepository.findPageableDto(page);
    }

    public Critic addCritic(Critic critic) {
        return criticRepository.save(critic);
    }

    public Critic convertDtoToEntity(CriticDto dto){
        Critic critic = new Critic();
        critic.setId(dto.getId());
        critic.setFirstName(dto.getFirstName());
        critic.setLastName(dto.getLastName());
        critic.setDescription(dto.getDescription());
        return critic;
    }

    public Critic addCriticDto(CriticDto critic) {
        return criticRepository.save(convertDtoToEntity(critic));
    }

    public CriticDto updateCritic(CriticDto newCritic, int id) {
        CriticDto result = getCriticById(id);
        result.setId(id);
        result.setFirstName(newCritic.getFirstName());
        result.setLastName(newCritic.getLastName());
        result.setDescription(newCritic.getDescription());
        criticRepository.save(convertDtoToEntity(result));
        return result;
    }

    //GET по id
    public CriticDto getCriticById(int id) {
        Integer idInt = id;
        return criticCriteriaRepository.convertEntityToDto(criticRepository.getOne(idInt));
    }

    //DELETE по id
    public boolean deleteCriticById(int id) {

        criticRepository.deleteById(id);
        return true;
    }
}
