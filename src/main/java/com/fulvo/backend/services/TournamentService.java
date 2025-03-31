package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.match.DateResponse;
import com.fulvo.backend.dto.match.FixtureResponse;
import com.fulvo.backend.dto.match.MatchResponse;
import com.fulvo.backend.dto.team.TeamTournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.models.*;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    private final UserService userService;
    private final ScoreboardService scoreboardService;
    private final TournamentTeamHelperService tournamentTeamHelperService;
    private final MatchService matchService;

    public GenericResponse createTournament(TournamentRequest request) {
        User admin = userService.getUser();

        boolean exist = tournamentRepository.existsByNameAndAdmin(request.getName(), admin);
        if(exist)
            throw new RuntimeException("Ya tenes un torneo con ese nombre");

        Tournament tournament = Tournament.builder()
                .name(request.getName())
                .admin(admin)
                .build();

        tournamentRepository.save(tournament);
        return GenericResponse.builder()
                .name(tournament.getName())
                .build();
    }

    public GenericResponse deleteTournament(TournamentRequest request) {
        User admin = userService.getUser();
        Tournament tournament = tournamentRepository.findByNameAndAdmin(request.getName(), admin)
                .orElseThrow(() -> new RuntimeException("No se encontro el torneo"));
        List <Scoreboard> scoreboardList = scoreboardService.findAllByTournament(tournament);
        if (!scoreboardList.isEmpty()){
            scoreboardService.deleteAll(scoreboardList);
        }
        tournamentRepository.deleteById(tournament.getId());
        return GenericResponse.builder()
                .name("Torneo eliminado")
                .message(request.getName())
                .build();
    }

    public GenericResponse joinTournament(TeamTournamentRequest request) {
        return tournamentTeamHelperService.inviteTournament(request);
    }

    public GenericResponse kickTeam(TeamTournamentRequest request) {
        return tournamentTeamHelperService.kickTeam(request);
    }

    public GenericResponse startTournament(TournamentRequest request) {
        User admin = userService.getUser();
        Tournament tournament = tournamentRepository.findByNameAndAdmin(request.getName(), admin)
                .orElseThrow(() -> new RuntimeException("No se encontro el torneo"));
        List<Scoreboard> scoreboardList = scoreboardService.findAllByTournament(tournament);
        List<Team> teams = tournamentTeamHelperService.getAllTeams(scoreboardList);

        Collections.shuffle(teams);
        matchService.generateMatches(teams);
        if (tournament.getRoundTrip()){
            teams.reversed();
            matchService.generateMatches(teams);
        }

        return GenericResponse.builder()
                .name("Fixture generado")
                .message("Partidos creados exitosamente")
                .build();
    }


    public FixtureResponse getFixture(TournamentRequest request) {
        Tournament tournament = tournamentRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se encontro el torneo"));
        return FixtureResponse.builder().build();
    }

    public DateResponse getFixture(TournamentRequest request, Integer date) {
        return DateResponse.builder().build();
    }

    public MatchResponse getMatch(){
        return MatchResponse.builder().build();
    }

}
