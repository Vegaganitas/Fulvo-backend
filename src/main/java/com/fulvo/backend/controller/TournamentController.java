package com.fulvo.backend.controller;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.team.JoinTournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PostMapping(value = "admin/create")
    public ResponseEntity<GenericResponse> createTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.createTournament(request));
    }

    @DeleteMapping(value = "admin/delete")
    public ResponseEntity<GenericResponse> deleteTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.deleteTournament(request));
    }

    @PostMapping(value = "admin/team/invite")
    public ResponseEntity<GenericResponse> inviteTeam(@RequestBody JoinTournamentRequest request){
        return ResponseEntity.ok(tournamentService.joinTournament(request));
    }

}
