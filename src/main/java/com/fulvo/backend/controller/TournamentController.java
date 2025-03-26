package com.fulvo.backend.controller;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PostMapping("admin/create")
    public ResponseEntity<GenericResponse> createTournament(@RequestBody TournamentRequest request){
        return ResponseEntity.ok(tournamentService.createTournament(request));
    }

}
