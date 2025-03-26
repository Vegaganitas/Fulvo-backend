package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.team.JoinTournamentRequest;
import com.fulvo.backend.dto.team.TeamRequest;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.repositories.ScoreboardRepository;
import com.fulvo.backend.repositories.TeamRepository;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final ScoreboardRepository scoreboardRepository;
    private final UserService userService;

    public GenericResponse createTeam(TeamRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .captain(userService.getUser())
                .build();

        teamRepository.save(team);
        return GenericResponse.builder()
                .name(team.getName())
                .build();
    }

    public GenericResponse joinTournament(JoinTournamentRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Tournament tournament = tournamentRepository.findById(request.getTournamentId())
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

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
                .message("Se registró al equipo")
                .build();
    }
}
