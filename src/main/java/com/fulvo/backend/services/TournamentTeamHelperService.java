package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.team.JoinTournamentRequest;
import com.fulvo.backend.dto.team.TeamRequest;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.models.User;
import com.fulvo.backend.repositories.TeamRepository;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentTeamHelperService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;

    private final UserService userService;
    private final ScoreboardService scoreboardService;

    public GenericResponse inviteTournament(JoinTournamentRequest request) {
        User admin = userService.getUser();
        Tournament tournament = tournamentRepository.findByIdAndAdmin(request.getTournamentId(), admin)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        return scoreboardService.joinTournament(team, tournament);
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
