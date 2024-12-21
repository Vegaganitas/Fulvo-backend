package com.fulvo.backend.controllers;

import com.fulvo.backend.models.League;
import com.fulvo.backend.services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leagues")
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    // Obtener todas las ligas
    @GetMapping("/all")
    public List<League> getAllLeagues() {
        return leagueService.getAllLeagues();
    }

    // Obtener una liga por ID
    @GetMapping("/{id}")
    public League getLeagueById(@PathVariable int id) {
        return leagueService.getLeagueById(id);
    }

    // Crear una nueva liga
    @PostMapping
    public League createLeague(@RequestBody League league) {
        return leagueService.createLeague(league);
    }

    // Eliminar una liga por ID
    @DeleteMapping("/{id}")
    public League deleteLeague(@PathVariable int id) {
        League league = leagueService.getLeagueById(id);
        if (league != null) {
            leagueService.deleteLeague(id);
        }
        return league;
    }

}
