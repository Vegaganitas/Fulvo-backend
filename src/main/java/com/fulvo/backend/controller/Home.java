package com.fulvo.backend.controller;

import com.fulvo.backend.dto.ScoreboardResponse;
import com.fulvo.backend.dto.match.DateResponse;
import com.fulvo.backend.dto.match.FixtureResponse;
import com.fulvo.backend.dto.match.MatchDTO;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentResponse;
import com.fulvo.backend.services.HomeService;
import com.fulvo.backend.services.MatchService;
import com.fulvo.backend.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("home")
@RequiredArgsConstructor
public class Home {

    private final TournamentService tournamentService;

    @GetMapping(value = "tournaments")
    public ResponseEntity<List<TournamentResponse>> getTournaments(){
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @GetMapping(value = "tournament/{id}/fixture")
    public ResponseEntity<FixtureResponse> getFixture(@PathVariable Integer id){
        return ResponseEntity.ok(tournamentService.getFixture(id));
    }

    @GetMapping(value = "tournament/{id}/fixture/{date}")
    public ResponseEntity<DateResponse> getMatchesByDate(@PathVariable Integer id, @PathVariable Integer date){
        return ResponseEntity.ok(tournamentService.getFixtureByDate(id, date));
    }

    @GetMapping(value = "tournament/{id}/table")
    public ResponseEntity<List<ScoreboardResponse>> getTable(@PathVariable Integer id){
        return ResponseEntity.ok(tournamentService.getTable(id));
    }

}
