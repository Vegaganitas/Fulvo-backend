package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.ScoreboardResponse;
import com.fulvo.backend.models.Match;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.repositories.ScoreboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreboardService {

    private final ScoreboardRepository scoreboardRepository;

    public GenericResponse createScoreboard(Team team, Tournament tournament){
        Scoreboard scoreboard = Scoreboard.builder()
                .team(team)
                .tournament(tournament)
                .gamesPlayed(0)
                .wins(0)
                .draws(0)
                .loses(0)
                .goalsFor(0)
                .goalsAgainst(0)
                .points(0)
                .build();
        scoreboardRepository.save(scoreboard);
        return GenericResponse.builder()
                .name("Registro exitoso")
                .message(team.getName() + " se ha registrado a " + tournament.getName())
                .build();
    }

    @Transactional
    public GenericResponse joinTournament (Team team, Tournament tournament){
        boolean exist = scoreboardRepository.existsByTeamAndTournament(team, tournament);
        if (exist){
            throw new RuntimeException("El equipo está anotado");
        }
        if (team.getId() != 0){
            if (tournament.getTeamsMax() == tournament.getTeams())
                throw new RuntimeException("Torneo con cupo completo");
            tournament.setTeams(tournament.getTeams() + 1);
        }
        return createScoreboard(team, tournament);
    }

    public List<Scoreboard> findAllByTeam (Team team){
        return scoreboardRepository.findAllByTeamId(team.getId())
                .orElseThrow(() -> new RuntimeException("El equipo no estaba registrado a ningun torneo"));
    }

    public List<Scoreboard> findAllByTournament(Tournament tournament){
        return scoreboardRepository.findAllByTournamentId(tournament.getId())
                .orElseThrow(() -> new RuntimeException("No habian equipos registrados"));
    }

    public Scoreboard findByTeamAndTournament(Team team, Tournament tournament) {
        return scoreboardRepository.findByTeamAndTournament(team, tournament)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado en torneo"));
    }

    @Transactional
    public void delete(Team team, Tournament tournament){
        scoreboardRepository.deleteByTeamAndTournament(team, tournament);
        tournament.setTeams(tournament.getTeams()-1);
    }

    @Transactional
    public void deleteAll(List<Scoreboard> scoreboardList) {
        scoreboardRepository.deleteAll(scoreboardList);
    }

    @Transactional
    public void updateByMatch(Match match) {
        Scoreboard home = match.getHomeTeam();
        Scoreboard away = match.getAwayTeam();

        int homeScore = match.getHomeGoals();
        int awayScore = match.getAwayGoals();

        home.incrementGamesPlayed();
        away.incrementGamesPlayed();

        if (homeScore > awayScore) {
            home.incrementWins();
            home.addPoints(3);
            away.incrementLosses();
        } else if (homeScore < awayScore) {
            away.incrementWins();
            away.addPoints(3);
            home.incrementLosses();
        } else {
            home.incrementDraws();
            away.incrementDraws();
            home.addPoints(1);
            away.addPoints(1);
        }

        scoreboardRepository.save(home);
        scoreboardRepository.save(away);
    }

    public ScoreboardResponse getScoreboardResponse(Scoreboard scoreboard) {
        return ScoreboardResponse.builder()
                .id(scoreboard.getId())
                .teamId(scoreboard.getTeam().getId())
                .teamName(scoreboard.getTeam().getName())
                .gamesPlayed(scoreboard.getGamesPlayed())
                .points(scoreboard.getPoints())
                .wins(scoreboard.getWins())
                .draws(scoreboard.getDraws())
                .loses(scoreboard.getLoses())
                .goalsFor(scoreboard.getGoalsFor())
                .goalsAgainst(scoreboard.getGoalsAgainst())
                .build();
    }
}
