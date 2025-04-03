package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.registration.RegistrationDTO;
import com.fulvo.backend.dto.team.TeamRequest;
import com.fulvo.backend.dto.team.TeamResponse;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentResponse;
import com.fulvo.backend.models.*;
import com.fulvo.backend.repositories.TeamRepository;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamTournamentService {
    private final TeamService teamService;
    private final TournamentService tournamentService;

    private final ParticipationService participationService;
    private final ScoreboardService scoreboardService;
    private final MatchService matchService;

    public GenericResponse inviteTeam(RegistrationDTO request) {
        Tournament tournament = tournamentService.authTournament(request.getTournamentId());
        Team team = teamService.getTeam(request.getTeamId());
        return participationService.invitation(team, tournament);
    }

    public GenericResponse startTournament(TournamentRequest request) {
        Tournament tournament = tournamentService.authTournament(request.getId());

        List<Scoreboard> teams = scoreboardService.findAllByTournament(tournament);
        if (teams.size() < tournament.getTeamsMin())
            throw new RuntimeException("No se alcanzó la cantidad mínima de equipos");

        if (teams.size() % 2 != 0){
            scoreboardService.joinTournament(teamService.getTeam(0),tournament);
            teams.add(scoreboardService
                    .findByTeamAndTournament(teamService.getTeam(0), tournament));
        }

        Collections.shuffle(teams);
        matchService.generateMatches(teams, !tournament.getRoundTrip());
        if (tournament.getRoundTrip()){
            matchService.generateMatches(teams, tournament.getRoundTrip());
        }

        return GenericResponse.builder()
                .name("Fixture generado")
                .message("Partidos creados exitosamente")
                .build();
    }

    public GenericResponse kickTeam(RegistrationDTO request) {
        Tournament tournament = tournamentService.authTournament(request.getTournamentId());
        Team team = teamService.getTeam(request.getTeamId());
        scoreboardService.delete(team, tournament);
        return GenericResponse.builder()
                .name("Equipo eliminado")
                .message(team.getName() + " ha sido eliminado de " + tournament.getName())
                .build();
    }

    public GenericResponse joinTournament(RegistrationDTO request) {
        Team team = teamService.authTeam(request.getTeamId());
        Tournament tournament = tournamentService.getTournament(request.getTournamentId());

        if(tournament.getIsPrivate()){
            return participationService.registration(team, tournament);
        }
        return scoreboardService.joinTournament(team, tournament);
    }

    public GenericResponse leaveTournament(RegistrationDTO request) {
        Team team = teamService.authTeam(request.getTeamId());
        Tournament tournament = tournamentService.getTournament(request.getTournamentId());
        scoreboardService.delete(team, tournament);
        return GenericResponse.builder()
                .name("Equipo eliminado")
                .message(team.getName() + " ha sido eliminado de " + tournament.getName())
                .build();
    }
}
