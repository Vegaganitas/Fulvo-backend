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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final ScoreboardService scoreboardService;
    private final UserService userService;
    private final TournamentTeamHelperService tournamentTeamHelperService;

    public GenericResponse createTeam(TeamRequest request) {
        User captain = userService.getUser();

        boolean exist = teamRepository.existsByNameAndCaptain(request.getName(), captain);
        if(exist)
            throw new RuntimeException("Ya tenes un equipo con ese nombre");

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
        return tournamentTeamHelperService.joinTournament(request);
    }

}
