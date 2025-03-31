package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.team.TeamTournamentRequest;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.models.User;
import com.fulvo.backend.repositories.TeamRepository;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentTeamHelperService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;

    private final UserService userService;
    private final ScoreboardService scoreboardService;


    /// Tournament Services ///
    public Tournament getTournament(Integer tournamentId){
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));
        return tournament;
    }

    public GenericResponse inviteTournament(TeamTournamentRequest request) {
        Tournament tournament = getTournament(request.getTournamentId());
        User admin = userService.getUser();
        if (admin != tournament.getAdmin()){
            throw new RuntimeException("No sos el administrador del torneo");
        }

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        return scoreboardService.joinTournament(team, tournament);
    }

    public GenericResponse kickTeam(TeamTournamentRequest request) {
        Tournament tournament = getTournament(request.getTournamentId());
        User admin = userService.getUser();
        if (admin != tournament.getAdmin()){
            throw new RuntimeException("No sos el administrador del torneo");
        }

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        scoreboardService.delete(team, tournament);
        return GenericResponse.builder()
                .name("Equipo eliminado")
                .message(team.getName() + " ha sido eliminado de " + tournament.getName())
                .build();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /// Team Services ///

    public Team getTeam(Integer teamId){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return team;
    }

    public GenericResponse joinTournament(TeamTournamentRequest request) {
        Team team = getTeam(request.getTeamId());
        User captain = userService.getUser();
        if (captain != team.getCaptain()){
            throw new RuntimeException("No sos el capitan del equipo");
        }

        Tournament tournament = tournamentRepository.findById(request.getTournamentId())
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        return scoreboardService.joinTournament(team, tournament);
    }

    public GenericResponse leaveTournament(TeamTournamentRequest request) {
        Team team = getTeam(request.getTeamId());
        User captain = userService.getUser();
        if (captain != team.getCaptain()){
            throw new RuntimeException("No sos el capitan del equipo");
        }

        Tournament tournament = tournamentRepository.findById(request.getTournamentId())
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        scoreboardService.delete(team, tournament);
        return GenericResponse.builder()
                .name("Equipo eliminado")
                .message(team.getName() + " ha sido eliminado de " + tournament.getName())
                .build();
    }

    public List<Team> getAllTeams(List<Scoreboard> scoreboardList) {
        List<Team> teams = new ArrayList<>();
        for(Scoreboard s : scoreboardList){
            teams.add(s.getTeam());
        }
        return teams;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

}
