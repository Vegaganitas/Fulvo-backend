package com.fulvo.backend.controllers;

import com.fulvo.backend.models.Match;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.services.MatchService;
import com.fulvo.backend.services.ScoreboardService;
import com.fulvo.backend.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private ScoreboardService scoreboardService;
    @Autowired
    private MatchService matchService;

    // Obtener todos los torneo
    @GetMapping("/all")
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    // Obtener una torneo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable int id) {
        Tournament tournament = tournamentService.getTournamentById(id);
        if (tournament != null) {
            return ResponseEntity.ok(tournament);  // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found
        }
    }

    // Crear una nueva torneo
    @PostMapping
    @Transactional
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        Tournament createdTournament = tournamentService.createTournament(tournament);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // 201 Created
                .body(createdTournament);
    }

    // Genera fixture de un torneo
    @PostMapping("{tournamentId}/matches")
    public ResponseEntity<?> generateMatches(@PathVariable int tournamentId) {
        List <Match> matches = tournamentService.generateFixtureByTournament(tournamentId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(matches);
    }

    // Eliminar una torneo por ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Tournament> deleteTournament(@PathVariable int id) {
        Tournament league = tournamentService.getTournamentById(id);
        if (league != null) {
            tournamentService.deleteTournament(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found
        }
    }

    // Eliminar todos los torneo
    @DeleteMapping("/all")
    @Transactional
    public ResponseEntity<List<Tournament>> deleteAllTournaments() {
        List<Tournament> leagues = tournamentService.getAllTournaments();
        if (leagues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 No Content
        } else {
            tournamentService.deleteAllTournaments();
            return ResponseEntity.ok(leagues);  // 200 OK
        }
    }

    // Eliminar todos los partidos de un torneo
    @DeleteMapping("/{tournament_id}/all")
    @Transactional
    public ResponseEntity<List<Match>> deleteAllMatchesByTournamentId(@PathVariable int tournament_id){
        List<Match> matches = matchService.getAllMatchByTournamentId(tournament_id);
        if (matches.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 No Content
        } else {
            matchService.deleteAllMatches(matches);
            return ResponseEntity.ok(matches);  // 200 OK
        }
    }


    // Eliminar todos los torneo de un Admin
    @DeleteMapping("/admin/{adminId}")
    @Transactional
    public ResponseEntity<List<Tournament>> deleteAllTournamentsByAdminId(@PathVariable int adminId) {
        List<Tournament> leagues = tournamentService.getAllTournamentsByAdminId(adminId);
        if (leagues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 No Content
        } else {
            tournamentService.deleteAllTournamentsByAdminId(adminId);
            return ResponseEntity.ok(leagues);  // 200 OK
        }
    }
}
