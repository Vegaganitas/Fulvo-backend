package com.fulvo.backend.services;

import com.fulvo.backend.models.Match;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.repositories.MatchRepository;
import com.fulvo.backend.repositories.ScoreboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamService teamService;

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
        List<Match> fixture = new ArrayList<>();
        int numberOfTeams = scoreboards.size();

        // Si el número de equipos es impar, añadimos un equipo "libre".
        if (numberOfTeams % 2 != 0) {
            Scoreboard withoutRival = new Scoreboard();
            withoutRival.setTeam(teamService.getTeamById(0));
            scoreboards.add(withoutRival);
            numberOfTeams++;
        }
        Tournament tournament = scoreboards.get(0).getTournament();
        int rounds = numberOfTeams - 1;
        int matchesPerRound = numberOfTeams / 2;

        // Generar jornadas
        for (int round = 0; round < rounds; round++) {
            for (int game = 0; game < matchesPerRound; game++) {
                Match match = new Match();
                match.setRound(round);
                match.setTournament(tournament);
                int home = (round + game) % (numberOfTeams - 1);
                int away = (numberOfTeams - 1 - game + round) % (numberOfTeams - 1);

                // El último equipo siempre rota, evitando duplicados.
                if (game == 0) {
                    away = numberOfTeams - 1;
                }
                match.setHomeTeam(scoreboards.get(home).getTeam());
                match.setAwayTeam(scoreboards.get(away).getTeam());
                // Agregar el partido al fixture
                fixture.add(match);
                // Guarda el partido en la base de datos
                matchRepository.save(match);
            }
        }

        return fixture;
//        List<Match> matches = new ArrayList<>();
//        int numberOfTeams = scoreboards.size();
//        for (int i = 0; i < numberOfTeams; i++) {
//            for (int j = i + 1; j < numberOfTeams; j++) {
//                Match match = new Match();
//                match.setTournament(scoreboards.get(i).getTournament());
//                match.setHomeTeam(scoreboards.get(i).getTeam());
//                match.setAwayTeam(scoreboards.get(j).getTeam());
//                matchRepository.save(match);
//                matches.add(match);
//            }
//        }
//
//        if (numberOfTeams % 2 != 0){
//            for (int i = 0; i < numberOfTeams; i++){
//                Match freeMatch = new Match();
//                freeMatch.setTournament(scoreboards.get(i).getTournament());
//                freeMatch.setHomeTeam(scoreboards.get(i).getTeam());
//                freeMatch.setAwayTeam(null);
//                matchRepository.save(freeMatch);
//                matches.add(freeMatch);
//            }
//        }
//
//        return matches;
    }

    // Eliminar un registro de la tabla match por ID
    public void deleteMatch(int id) {
        matchRepository.deleteById(id);
    }

    // Eliminar un listado de partidos
    public void deleteAllMatches(List<Match> matches){
        matchRepository.deleteAll(matches);
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
