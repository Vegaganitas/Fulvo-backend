package com.fulvo.backend.controller;

import com.fulvo.backend.dto.match.DateResponse;
import com.fulvo.backend.dto.match.FixtureResponse;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("home")
@RequiredArgsConstructor
public class Home {

    private final TournamentService tournamentService;

    @GetMapping(value = "tournament/fixture")
    public ResponseEntity<FixtureResponse> getFixture(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.getFixture(request));
    }

    @GetMapping(value = "tournament/fixture/date/{date}")
    public ResponseEntity<DateResponse> getMatchesByDate(@RequestBody TournamentRequest request, @PathVariable Integer date){
        return ResponseEntity.ok(tournamentService.getFixture(request, date));
    }
}
