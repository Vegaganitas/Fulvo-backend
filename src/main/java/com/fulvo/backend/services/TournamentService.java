package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.ScoreboardResponse;
import com.fulvo.backend.dto.match.DateResponse;
import com.fulvo.backend.dto.match.FixtureResponse;
import com.fulvo.backend.dto.match.MatchDTO;
import com.fulvo.backend.dto.registration.RegistrationDTO;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentResponse;
import com.fulvo.backend.models.*;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    private final UserService userService;
    private final ScoreboardService scoreboardService;
    private final MatchService matchService;
    private final ParticipationService participationService;

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
                .isPrivate(request.getIsPrivate())
                .build();

        tournamentRepository.save(tournament);
        return getTournamentResponse(tournament);
    }

    public GenericResponse deleteTournament(TournamentRequest request) {
        Tournament tournament = authTournament(request.getId());
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

    public Tournament authTournament(Integer id){
        Tournament tournament = getTournament(id);
        User admin = userService.getUser();
        if (admin != tournament.getAdmin()){
            throw new RuntimeException("No sos el administrador del torneo");
        }
        return tournament;
    }

    public Tournament getTournament(Integer id){
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));
    }

    public TournamentResponse getTournamentResponse(Tournament tournament) {
        return TournamentResponse.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .adminName(tournament.getAdmin().getLastName() + ", " + tournament.getAdmin().getFirstName())
                .isPrivate(tournament.getIsPrivate())
                .build();
    }

    public List<TournamentResponse> getMyTournaments() {
        User admin = userService.getUser();
        List<Tournament> tournaments = tournamentRepository.findAllByAdmin(admin);
        return tournaments.stream()
                .map(this::getTournamentResponse)
                .collect(Collectors.toList());
    }

    public TournamentResponse selectTournament(TournamentRequest request) {
        Tournament tournament = authTournament(request.getId());
        return getTournamentResponse(tournament);
    }

    public GenericResponse approveRequest(RegistrationDTO request){
        Tournament tournament = authTournament(request.getTournamentId());
        TournamentRegistration registration = participationService.findRegistrationById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se ha encontrado la solicitud"));

        registration.setStatus(StatusParticipation.ACCEPT);
        participationService.save(registration);
        return scoreboardService.joinTournament(registration.getTeam(), tournament);
    }

    public GenericResponse rejectRequest(RegistrationDTO request){
        Tournament tournament = authTournament(request.getTournamentId());
        TournamentRegistration registration = participationService.findRegistrationById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se ha encontrado la solicitud"));

        registration.setStatus(StatusParticipation.DECLINE);
        participationService.save(registration);
        return GenericResponse.builder()
                .name("Solicitud rechazada")
                .message("Se rechazó la solicitud de " + registration.getTeam().getName())
                .build();
    }

    public List<TournamentResponse> getAllTournaments(){
        List<Tournament> tournaments = tournamentRepository.findAll();
        return tournaments.stream()
                .map(this::getTournamentResponse)
                .collect(Collectors.toList());
    }

    public List<RegistrationDTO> getPendingRequests(TournamentRequest request) {
        Tournament tournament = authTournament(request.getId());
        List<TournamentRegistration> pendings = participationService.findRegistrationsPending(tournament, StatusParticipation.PENDING);
        return pendings.stream()
                .map(participationService::getRegistrationResponse)
                .collect(Collectors.toList());
    }

    private DateResponse getDateResponse(Tournament tournament, Integer date){
        List<Match> matches = matchService.getMatchesByDate(tournament, date);
        List<MatchDTO> matchResponse = matches.stream()
                .map(matchService::getMatchResponse)
                .collect(Collectors.toList());
        return DateResponse.builder()
                .date(date)
                .matches(matchResponse)
                .build();
    }

    public DateResponse getFixtureByDate(Integer id, Integer date){
        Tournament tournament = getTournament(id);
        return getDateResponse(tournament, date);
    }

    public FixtureResponse getFixture(Integer id) {
        Tournament tournament = getTournament(id);
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

    public List<ScoreboardResponse> getTable(Integer id) {
        Tournament tournament = getTournament(id);
        List<Scoreboard> scoreboards = scoreboardService.findAllByTournament(tournament);
        return scoreboards.stream()
                .map(scoreboardService::getScoreboardResponse)
                .collect(Collectors.toList());
    }
}
