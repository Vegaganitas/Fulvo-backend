package com.fulvo.backend.services;

import com.fulvo.backend.models.Match;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    // Obtener todos los registros de la tabla match
    public List<Match> getAllMatch() {
        return matchRepository.findAll();
    }

    // Obtener un registro de la tabla match por ID
    public Match getMatchById(int id) {
        return matchRepository.findById(id).orElse(null);
    }

    // Crear un nuevo registro en la tabla match
    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    // Generar partidos
    public List<Match> generateMatches(List<Scoreboard> scoreboards) {
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < scoreboards.size(); i++) {
            for (int j = i + 1; j < scoreboards.size(); j++) {
                Match match = new Match();
                match.setTournament(scoreboards.get(i).getTournament());
                match.setHomeTeam(scoreboards.get(i).getTeam());
                match.setAwayTeam(scoreboards.get(j).getTeam());
                matchRepository.save(match);
                matches.add(match);
            }
        }
        return matches;
    }

    // Eliminar un registro de la tabla match por ID
    public void deleteMatch(int id) {
        matchRepository.deleteById(id);
    }

    // Actualizar un registro de la tabla match por ID
    public Match updateMatch(int id, Match match) {
        Match matchToUpdate = matchRepository.findById(id).orElse(null);
        if (matchToUpdate != null) {
            matchToUpdate.setTournament(match.getTournament());
            matchToUpdate.setHomeTeam(match.getHomeTeam());
            matchToUpdate.setAwayTeam(match.getAwayTeam());
            matchToUpdate.setHomeTeamScore(match.getHomeTeamScore());
            matchToUpdate.setAwayTeamScore(match.getAwayTeamScore());
            matchToUpdate.setResult(match.getResult());
            matchToUpdate.setDate(match.getDate());
            matchToUpdate.setRound(match.getRound());
            matchToUpdate.setMvp(match.getMvp());
            return matchRepository.save(matchToUpdate);
        }
        return null;
    }

    // Obtener todos los registros de la tabla match por ID de torneo
    public List<Match> getAllMatchByTournamentId(int id) {
        return matchRepository.findAllByTournamentId(id);
    }
}
