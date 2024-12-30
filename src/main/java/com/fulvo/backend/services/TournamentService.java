package com.fulvo.backend.services;

import com.fulvo.backend.models.Match;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.repositories.MatchRepository;
import com.fulvo.backend.repositories.ScoreboardRepository;
import com.fulvo.backend.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private ScoreboardRepository scoreboardRepository;
    @Autowired
    private MatchService matchService;

    // Obtener todas las ligas
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    // Obtener una liga por su id
    public Tournament getTournamentById(int id) {
        return tournamentRepository.findById(id).orElse(null);
    }

    // Obtener todas las ligas de un Admin
    public List<Tournament> getAllTournamentsByAdminId(int adminId) {
        return tournamentRepository.findAllByAdminId(adminId);
    }

    // Crear una liga
    public Tournament createTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    // Eliminar una liga
    public void deleteTournament(int id) {
        tournamentRepository.deleteById(id);
    }

    // Eliminar todas las ligas
    public void deleteAllTournaments() {
        tournamentRepository.deleteAll();
    }

    // Eliminar todas las ligas de un Admin
    public void deleteAllTournamentsByAdminId(int adminId) {
        tournamentRepository.deleteAllByAdminId(adminId);
    }

    // Generar fixture de un torneo
    public List<Match> generateFixtureByTournament(int tournament){
        List<Scoreboard> scoreboards = scoreboardRepository.findAllByTournamentId(tournament);
        return matchService.generateMatches(scoreboards);
    }

}
