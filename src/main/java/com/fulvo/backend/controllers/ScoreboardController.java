package com.fulvo.backend.controllers;

import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.services.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scoreboards")
public class ScoreboardController {

    @Autowired
    private ScoreboardService scoreboardService;

    // Obtener todos los registros de la tabla scoreboard
    @GetMapping("/all")
    public List<Scoreboard> getAllScoreboard() {
        return scoreboardService.getAllScoreboard();
    }

    // Obtener todos los registros de la tabla scoreboard por ID de torneo
    @GetMapping("/tournament/{id}")
    public List<Scoreboard> getAllScoreboardByTournamentId(@PathVariable int id) {
        return scoreboardService.getAllScoreboardByTournamentId(id);
    }

    // Obtener un registro de la tabla scoreboard por ID
    @GetMapping("/{id}")
    @Transactional
    public Scoreboard getScoreboardById(@PathVariable int id) {
        return scoreboardService.getScoreboardById(id);
    }

    // Crear un nuevo registro en la tabla scoreboard
    @PostMapping
    @Transactional
    public Scoreboard createScoreboard(@RequestBody Scoreboard scoreboard) {
        return scoreboardService.createScoreboard(scoreboard);
    }

    // Eliminar un registro de la tabla scoreboard por ID
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteScoreboard(@PathVariable int id) {
        scoreboardService.deleteScoreboard(id);
    }

}
