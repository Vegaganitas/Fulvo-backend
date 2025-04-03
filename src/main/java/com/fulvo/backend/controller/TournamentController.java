package com.fulvo.backend.controller;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.match.MatchDTO;
import com.fulvo.backend.dto.registration.RegistrationDTO;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentResponse;
import com.fulvo.backend.services.MatchService;
import com.fulvo.backend.services.MatchTournamentService;
import com.fulvo.backend.services.TeamTournamentService;
import com.fulvo.backend.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;
    private final TeamTournamentService teamTournamentService;
    private final MatchTournamentService matchTournamentService;

    @PostMapping(value = "create")
    public ResponseEntity<TournamentResponse> createTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.createTournament(request));
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<GenericResponse> deleteTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.deleteTournament(request));
    }

    @PostMapping(value = "team/invite")
    public ResponseEntity<GenericResponse> inviteTeam(@RequestBody RegistrationDTO request){
        return ResponseEntity.ok(teamTournamentService.inviteTeam(request));
    }

    @DeleteMapping(value = "team/kick")
    public ResponseEntity<GenericResponse> kickTeam(@RequestBody RegistrationDTO request){
        return ResponseEntity.ok(teamTournamentService.kickTeam(request));
    }

    @PostMapping(value = "start")
    public ResponseEntity<GenericResponse> startTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(teamTournamentService.startTournament(request));
    }

    @GetMapping(value = "my-tournaments")
    public ResponseEntity<List<TournamentResponse>> myTournaments(){
        return ResponseEntity.ok(tournamentService.getMyTournaments());
    }

    @PostMapping(value = "select")
    public ResponseEntity<TournamentResponse> selectTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.selectTournament(request));
    }

    @GetMapping(value = "team/requests")
    public ResponseEntity<List<RegistrationDTO>> getPendingRequests(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.getPendingRequests(request));
    }

    @PostMapping(value = "team/request/approve")
    public ResponseEntity<GenericResponse> approveRequest(@RequestBody RegistrationDTO registration){
        return ResponseEntity.ok(tournamentService.approveRequest(registration));
    }

    @PostMapping(value = "team/request/reject")
    public ResponseEntity<GenericResponse> rejectRequest(@RequestBody RegistrationDTO registration){
        return ResponseEntity.ok(tournamentService.rejectRequest(registration));
    }

    @PostMapping(value = "match/ended")
    public ResponseEntity<MatchDTO> matchEnded(@RequestBody MatchDTO request){
        return ResponseEntity.ok(matchTournamentService.matchEnded(request));
    }

}
