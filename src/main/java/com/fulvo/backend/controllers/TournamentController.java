package com.fulvo.backend.controllers;

import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments") // DEBERÍA LLAMARSE TOURNAMENTS
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    // Obtener todas las ligas
    @GetMapping("/all")
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    // Obtener una liga por ID
    @GetMapping("/{id}")
    public Tournament getTournamentById(@PathVariable int id) {
        return tournamentService.getTournamentById(id);
    }

    // Crear una nueva liga
    @PostMapping
    public Tournament createTournament(@RequestBody Tournament tournament) {
        return tournamentService.createTournament(tournament);
    }

    // Eliminar una liga por ID
    @DeleteMapping("/{id}")
    public Tournament deleteTournament(@PathVariable int id) {
        Tournament league = tournamentService.getTournamentById(id);
        if (league != null) {
            tournamentService.deleteTournament(id);
        }
        return league;
    }

    // Eliminar todas las ligas
    @DeleteMapping("/all")
    public List<Tournament> deleteAllTournaments() {
        List<Tournament> leagues = tournamentService.getAllTournaments();
        tournamentService.deleteAllTournaments();
        return leagues;
    }

    // Eliminar todas las ligas de un Admin
    @DeleteMapping("/admin/{adminId}")
    public List<Tournament> deleteAllTournamentsByAdminId(@PathVariable int adminId) {
        List<Tournament> leagues = tournamentService.getAllTournamentsByAdminId(adminId);
        tournamentService.deleteAllTournamentsByAdminId(adminId);
        return leagues;
    }

}
