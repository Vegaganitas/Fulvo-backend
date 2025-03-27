package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.team.JoinTournamentRequest;
import com.fulvo.backend.dto.team.TeamRequest;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.models.User;
import com.fulvo.backend.repositories.ScoreboardRepository;
import com.fulvo.backend.repositories.TeamRepository;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final ScoreboardService scoreboardService;
    private final UserService userService;

    public GenericResponse createTeam(TeamRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .captain(userService.getUser())
                .build();

        teamRepository.save(team);
        return GenericResponse.builder()
                .name("Equipo creado")
                .message(team.getName())
                .build();
    }

    public GenericResponse deleteTeam(TeamRequest request) {
        User captain = userService.getUser();
        Team team = teamRepository.findByNameAndCaptain(request.getName(), captain)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        List<Scoreboard> scoreboardList = scoreboardService.findAllByTeam(team);
        if (!scoreboardList.isEmpty()){
            scoreboardService.deleteAll(scoreboardList);
        }

        teamRepository.deleteById(team.getId());
        return GenericResponse.builder()
                .name("Equipo eliminado")
                .message(request.getName())
                .build();
    }

    public GenericResponse joinTournament(JoinTournamentRequest request) {
        User captain = userService.getUser();
        Team team = teamRepository.findByIdAndCaptain(request.getTeamId(), captain)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Tournament tournament = tournamentRepository.findById(request.getTournamentId())
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        return scoreboardService.joinTournament(team, tournament);
    }


}
