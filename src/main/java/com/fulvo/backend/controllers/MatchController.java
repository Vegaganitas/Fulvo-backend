package com.fulvo.backend.controllers;

import com.fulvo.backend.models.Match;
import com.fulvo.backend.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    // Obtener todos los registros de la tabla match
    @GetMapping("/all")
    public List<Match> getAllMatch() {
        return matchService.getAllMatch();
    }

    // Obtener un registro de la tabla match por ID
    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable int id) {
        return matchService.getMatchById(id);
    }

    // Crear un nuevo registro en la tabla match
    @PostMapping
    @Transactional
    public Match createMatch(@RequestBody Match match) {
        return matchService.createMatch(match);
    }

    // Eliminar un registro de la tabla match por ID
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteMatch(@PathVariable int id) {
        matchService.deleteMatch(id);
    }

    // Actualizar un registro de la tabla match por ID
    @PutMapping("/{id}")
    @Transactional
    public Match updateMatch(@PathVariable int id, @RequestBody Match match) {
        return matchService.updateMatch(id, match);
    }

    // Obtener todos los registros de la tabla match por ID de torneo
    @GetMapping("/tournament/{id}")
    public List<Match> getAllMatchByTournamentId(@PathVariable int id) {
        return matchService.getAllMatchByTournamentId(id);
    }

}
