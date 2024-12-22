package com.fulvo.backend.controllers;

import com.fulvo.backend.models.Team;
import com.fulvo.backend.services.TeamService;
import com.fulvo.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    // Obtener todos los equipos
    @GetMapping("/all")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    // Obtener un equipo por ID
    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable int id) {
        return teamService.getTeamById(id);
    }

    // Obtener todos los equipos de un capitán
    @GetMapping("/captain/{id}")
    public List<Team> getTeamsByCaptain(@PathVariable int id) {
        return teamService.getTeamsByCaptain(id);
    }

    // Crear un nuevo equipo
    @PostMapping
    @Transactional
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    // Eliminar un equipo por ID
    @DeleteMapping("/{id}")
    @Transactional
    public Team deleteTeam(@PathVariable int id) {
        Team team = teamService.getTeamById(id);
        if (team != null) {
            teamService.deleteTeam(id);
        }
        return team;
    }

    // Eliminar todos los equipos de un capitán
    @DeleteMapping("/captain/{id}")
    @Transactional
    public List<Team> deleteTeamsByCaptain(@PathVariable int id) {
        return teamService.deleteTeamByCaptain(id);
    }

}
