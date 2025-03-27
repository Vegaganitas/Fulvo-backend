package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
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

    public GenericResponse joinTournament (Team team, Tournament tournament){
        boolean exist = scoreboardRepository.existsByTeamAndTournament(team, tournament);
        if (exist){
            throw new RuntimeException("El equipo está anotado");
        }

        Scoreboard scoreboard = Scoreboard.builder()
                .team(team)
                .tournament(tournament)
                .build();

        scoreboardRepository.save(scoreboard);
        return GenericResponse.builder()
                .name(team.getName())
                .message("Se ha registrado")
                .build();
    }

    public List<Scoreboard> findAllByTeam (Team team){
        return scoreboardRepository.findAllByTeamId(team.getId());
    }

    public void deleteAll(List<Scoreboard> scoreboardList) {
        scoreboardRepository.deleteAll(scoreboardList);
    }
}
