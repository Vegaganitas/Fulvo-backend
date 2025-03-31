package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.match.DateResponse;
import com.fulvo.backend.dto.match.FixtureResponse;
import com.fulvo.backend.dto.match.MatchResponse;
import com.fulvo.backend.dto.team.TeamTournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentResponse;
import com.fulvo.backend.models.*;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    private final UserService userService;
    private final ScoreboardService scoreboardService;
    private final TournamentTeamHelperService tournamentTeamHelperService;
    private final MatchService matchService;

    public TournamentResponse createTournament(TournamentRequest request) {
        User admin = userService.getUser();

        boolean exist = tournamentRepository.existsByNameAndAdmin(request.getName(), admin);
        if(exist)
            throw new RuntimeException("Ya tenes un torneo con ese nombre");

        Tournament tournament = Tournament.builder()
                .name(request.getName())
                .teamsMax(request.getTeamsMax())
                .teamsMin(request.getTeamsMin())
                .roundTrip(request.getRoundTrip())
                .teams(0)
                .admin(admin)
                .build();

        tournamentRepository.save(tournament);
        return TournamentResponse.builder()
                .name(tournament.getName())
                .teamsMax(tournament.getTeamsMax())
                .teamsMin(tournament.getTeamsMin())
                .build();
    }

    public GenericResponse deleteTournament(TournamentRequest request) {
        User admin = userService.getUser();
        Tournament tournament = tournamentRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se encontro el torneo"));
        if (admin != tournament.getAdmin())
            throw new RuntimeException("No sos el administrador del torneo");

        List<Match> matchList = matchService.findAllByTournament(tournament);
        if (!matchList.isEmpty()){
            matchService.deleteAll(matchList);
        }

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
        Tournament tournament = tournamentRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se encontro el torneo"));
        if (admin != tournament.getAdmin())
            throw new RuntimeException("No sos el administrador del torneo");

        List<Scoreboard> teams = scoreboardService.findAllByTournament(tournament);
        if (teams.size() < tournament.getTeamsMin())
            throw new RuntimeException("No se alcanzó la cantidad mínima de equipos");

        if (teams.size() % 2 != 0){
            scoreboardService.joinTournament(tournamentTeamHelperService.getTeam(0),tournament);
            teams.add(scoreboardService
                    .findByTeamAndTournament(tournamentTeamHelperService.getTeam(0), tournament));
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


    public FixtureResponse getFixture(TournamentRequest request) {
        Tournament tournament = tournamentRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se encontro el torneo"));
        int dates = tournament.getRoundTrip() ? (tournament.getTeams()-1) * 2 : tournament.getTeams()-1;
        List<DateResponse> matches = new ArrayList<>();
        for(int i=1; i<=dates; i++){
            matches.add(getDateResponse(tournament, i));
        }

        return FixtureResponse.builder()
                .tournament(tournament.getName())
                .matches(matches)
                .build();
    }


    public DateResponse getFixture(TournamentRequest request, Integer date) {
        Tournament tournament = tournamentRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se encontro el torneo"));

        return getDateResponse(tournament, date);
    }

    private DateResponse getDateResponse(Tournament tournament, int date){
        List<Match> matches = matchService.getMatchesByDate(tournament, date);
        List<MatchResponse> matchResponses = matches.stream()
                .map(this::getMatchResponse)
                .collect(Collectors.toList());
        return DateResponse.builder()
                .date(date)
                .matches(matchResponses)
                .build();
    }

    public MatchResponse getMatchResponse(Match match){
        return MatchResponse.builder()
                .homeTeam(match.getHomeTeam().getTeam().getName())
                .awayTeam(match.getAwayTeam().getTeam().getName())
                .day(match.getDay())
                .date(match.getDate())
                .build();
    }

}
