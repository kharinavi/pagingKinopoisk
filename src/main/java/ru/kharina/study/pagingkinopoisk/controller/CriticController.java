package ru.kharina.study.pagingkinopoisk.controller;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kharina.study.pagingkinopoisk.dto.CriticDto;
import ru.kharina.study.pagingkinopoisk.model.Critic;
import ru.kharina.study.pagingkinopoisk.model.CriticPage;
import ru.kharina.study.pagingkinopoisk.service.CriticService;

@Api(value = "Контроллер для работы с критиками")
@RestController
@RequestMapping("/critic")
public class CriticController {
    private final CriticService criticService;

    public CriticController(CriticService criticService) {
        this.criticService = criticService;
    }

    @GetMapping
    public ResponseEntity<Page<CriticDto>> getCritics(CriticPage page){
        Page<CriticDto> criticPages = criticService.getCritics(page);
        return new ResponseEntity<>(criticPages, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Critic> addCritic(@RequestBody CriticDto critic){
        return new ResponseEntity<>(criticService.addCriticDto(critic), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    CriticDto getCriticDtoById(@PathVariable int id) {
        return criticService.getCriticById(id);
    }

    @PutMapping("/{id}")
    CriticDto updateCritic(@RequestBody CriticDto newCritic, @PathVariable int id) {
        return criticService.updateCritic(newCritic, id);
    }

    @DeleteMapping("/{id}")
    void deleteCritic(@PathVariable int id) {
        criticService.deleteCriticById(id);
    }
}
