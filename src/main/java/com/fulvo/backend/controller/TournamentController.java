package com.fulvo.backend.controller;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.match.DateResponse;
import com.fulvo.backend.dto.match.FixtureResponse;
import com.fulvo.backend.dto.team.TeamTournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PostMapping(value = "create")
    public ResponseEntity<GenericResponse> createTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.createTournament(request));
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<GenericResponse> deleteTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.deleteTournament(request));
    }

    @PostMapping(value = "team/invite")
    public ResponseEntity<GenericResponse> inviteTeam(@RequestBody TeamTournamentRequest request){
        return ResponseEntity.ok(tournamentService.joinTournament(request));
    }

    @DeleteMapping(value = "team/kick")
    public ResponseEntity<GenericResponse> kickTeam(@RequestBody TeamTournamentRequest request){
        return ResponseEntity.ok(tournamentService.kickTeam(request));
    }

    @PostMapping(value = "start")
    public ResponseEntity<GenericResponse> startTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.startTournament(request));
    }

    @GetMapping(value = "fixture")
    public ResponseEntity<FixtureResponse> getFixture(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.getFixture(request));
    }

    @GetMapping(value = "date{date}")
    public ResponseEntity<DateResponse> getMatchesByDate(@RequestBody TournamentRequest request, @PathVariable Integer date){
        return ResponseEntity.ok(tournamentService.getFixture(request, date));
    }

}
