package com.fulvo.backend.services;

import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.repositories.ScoreboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreboardService {

    @Autowired
    private ScoreboardRepository scoreboardRepository;

    // Obtener todos los registros de la tabla scoreboard
    public List<Scoreboard> getAllScoreboard() {
        return scoreboardRepository.findAll();
    }

    // Obtener un registro de la tabla scoreboard por ID
    public Scoreboard getScoreboardById(int id) {
        return scoreboardRepository.findById(id).orElse(null);
    }

    // Obtener todos los registros de la tabla scoreboard por ID de torneo
    public List<Scoreboard> getAllScoreboardByTournamentId(int id) {
        return scoreboardRepository.findAllByTournamentId(id);
    }

    // Crear un nuevo registro en la tabla scoreboard
    public Scoreboard createScoreboard(Scoreboard scoreboard) {
        return scoreboardRepository.save(scoreboard);
    }

    // Eliminar un registro de la tabla scoreboard por ID
    public void deleteScoreboard(int id) {
        scoreboardRepository.deleteById(id);
    }

}
