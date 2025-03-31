package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.repositories.ScoreboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .gamesWon(0)
                .gamesDraw(0)
                .gamesLost(0)
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

    public GenericResponse joinTournament (Team team, Tournament tournament){
        boolean exist = scoreboardRepository.existsByTeamAndTournament(team, tournament);
        if (exist){
            throw new RuntimeException("El equipo está anotado");
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

    public void delete(Team team, Tournament tournament){
        scoreboardRepository.deleteByTeamAndTournament(team, tournament);
    }

    public void deleteAll(List<Scoreboard> scoreboardList) {
        scoreboardRepository.deleteAll(scoreboardList);
    }

}
